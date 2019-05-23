package com.example.locationalarm

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var latlng: LatLng
    private lateinit var name: String
    private lateinit var location: String
    private var radius: Double = 0.0
    private lateinit var alert: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //USE THIS TO PUT DATA FOR ALARM INTENT: intent.putExtra("package.name.key", "value");
        var extras = intent.extras //All Activities are started with an Intent
        val lat = extras.getDouble("latitude")
        val long = extras.getDouble("longitude")
        latlng = LatLng(lat, long)
        name = extras.getString("name")
        location = extras.getString( "location")
        radius = extras.getDouble("radius")
        alert = extras.getString("alert")
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
        mapSetMarker(mMap, latlng)
    }

    //Set Marker for passed Alarm Intent
    fun mapSetMarker(googleMap: GoogleMap, alarmLatlng: LatLng) {
        mMap = googleMap

        // Add a marker at Alarm Location and move the camera
        val alarm = alarmLatlng
        mMap.addMarker(MarkerOptions().position(alarm).title("Location Alarm Set Here"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(alarm))
    }
}
