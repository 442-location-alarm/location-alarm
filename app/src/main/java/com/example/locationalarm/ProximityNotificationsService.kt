package com.example.locationalarm

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.IBinder
import androidx.core.app.ActivityCompat

class ProximityNotificationsService : Service() {

    private val PROX_ALERT_INTENT = "com.example.locationalarm.ProximityAlert"

    override fun onCreate() {
        super.onCreate()
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val intent = Intent(PROX_ALERT_INTENT)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

            // TODO change these to values received from the intent once Maddie is done with the Search acitivity
            val latitude: Double = -500.0
            val longitude: Double = -500.0
            val radius: Float = getMeters(intent.extras.getDouble("radius"))
            val expiration: Long = -1

            locationManager.addProximityAlert(
                latitude,     // the latitude of the central point of the alert region
                longitude,    // the longitude of the central point of the alert region
                radius,       // the radius of the central point of the alert region, in meters
                expiration,   // time for this proximity alert, in milliseconds, or -1 to indicate no expiration
                pendingIntent // will be used to generate an Intent to fire when entry to or exit from the alert region is detected
            )

            val filter = IntentFilter(PROX_ALERT_INTENT)
            registerReceiver(ProximityIntentReceiver(), filter)
        }

    }

    private fun getMeters(i: Double): Float {
        return (i * 1609.344).toFloat()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
