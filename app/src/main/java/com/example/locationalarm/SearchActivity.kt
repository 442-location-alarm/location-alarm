package com.example.locationalarm

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
//import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.IOException
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.*

class SearchActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {
    override fun onMarkerClick(p0: Marker?) = false


    private lateinit var mMap: GoogleMap
    private var latlng: LatLng = LatLng(-500.0, -500.0)
    private var locationName: String = ""
    private var locationAddress: String = ""
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    companion object {
        // don't change this back to private because it is used in the Proximity files for notifications!
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
        private const val PLACE_PICKER_REQUEST = 3
        private const val AUTOCOMPLETE_REQUEST_CODE = 4
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
            }
        }

        createLocationRequest()

        loadPlacePicker()
    }

    //Initial request for location based permissions
    private fun getLocationPermission() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                latlng = LatLng(location.latitude, location.longitude)
                mapSetMarker(latlng)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15f))
            }
        }
    }

    //Use GeoCoder to get an Address from a LatLng
    private fun getAddress(latLng: LatLng): String {
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try{
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (null !=  addresses && addresses.isNotEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex + 1) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            Log.e("SearchActivity", e.localizedMessage)
        }
        return addressText
    }

    private fun loadPlacePicker() {
        Places.initialize(this, "AIzaSyC_1cy-X2JKlHeS9r9YJcuaAlm8ELiZBRo")
        val placesClient = Places.createClient(this)
        val fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)
        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(this)
        try {
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(this@SearchActivity, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    //ignore the  error
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }
        //Retrieve Place Picker information and place marker at result
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data!!)
                latlng = place.latLng!!
                Log.d("SearchActivity", place.toString())
                locationAddress = place.address.toString()
                locationName = place.name.toString()

                mapSetMarker(latlng)
                Log.i("SearchActivity", "Place: " + place.name + ", " + place.id);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                val status = Autocomplete.getStatusFromIntent(data!!)
                Log.i("SearchActivity", status.statusMessage);
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    public override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

    //send Alarm Location Data in an Intent
    private fun sendIntent() {
        val extras = intent.extras
        val searchIntent = Intent(this@SearchActivity, CreateAlarmActivity::class.java)

        if (locationAddress == "") {
            locationAddress = getAddress(latlng)
        }

        var alarmLocation: String
        if (locationName == "") {
            alarmLocation = locationAddress
        } else {
            alarmLocation = locationName
        }

        // check if alarm data is sent via intent and send that to create alarm :D
        if (intent.hasExtra("edit")) {
            searchIntent.putExtra("edit", 1)

            val alarm = extras.getParcelable("alarm") as Alarm
            alarm.location = alarmLocation
            alarm.latitude = latlng.latitude
            alarm.longitude = latlng.longitude

            searchIntent.putExtra("alarm", alarm)
        } else {
            searchIntent.putExtra("address", alarmLocation)
            searchIntent.putExtra("latitude", latlng.latitude)
            searchIntent.putExtra("longitude", latlng.longitude)
        }

        startActivity(searchIntent)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMarkerClickListener(this)
        mMap.uiSettings.isZoomControlsEnabled = true

        getLocationPermission()

        mMap.setOnMapClickListener {
            mapSetMarker(it)
        }
    }

    // Add a marker at Alarm Location and move the camera
    fun mapSetMarker(alarmLatlng: LatLng) {
        latlng = alarmLatlng
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(latlng).title("Place an Alarm for this Location"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15f))
        mMap.setOnInfoWindowClickListener(object: GoogleMap.OnInfoWindowClickListener {
            override fun onInfoWindowClick(marker: Marker) {
                sendIntent()
            }
        })
    }
}
