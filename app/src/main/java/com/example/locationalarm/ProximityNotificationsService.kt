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
import android.util.Log
import androidx.core.app.ActivityCompat

class ProximityNotificationsService : Service() {

    private val PROX_ALERT_INTENT = "com.example.locationalarm.ProximityAlert"
    private lateinit var intent: Intent
    private val receiver = ProximityIntentReceiver()

    override fun onCreate() {
        super.onCreate()
    }

    private fun getMeters(i: Double): Float {
        return (i * 1609.344).toFloat()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val latitude: Double = intent!!.extras.getDouble("latitude")
            val longitude: Double = intent!!.extras.getDouble("longitude")
            val radius: Float = getMeters(intent!!.extras.getDouble("radius"))
            val expiration: Long = -1

            val proxIntent = Intent(PROX_ALERT_INTENT)
                proxIntent.putExtra("alert", intent.extras.getString("alert"))
            val pendingIntent = PendingIntent.getBroadcast(this, 0, proxIntent, PendingIntent.FLAG_CANCEL_CURRENT)
            locationManager.addProximityAlert(
                latitude,     // the latitude of the central point of the alert region
                longitude,    // the longitude of the central point of the alert region
                radius,       // the radius of the central point of the alert region, in meters
                expiration,   // time for this proximity alert, in milliseconds, or -1 to indicate no expiration
                pendingIntent // will be used to generate an Intent to fire when entry to or exit from the alert region is detected
            )

            val filter = IntentFilter(PROX_ALERT_INTENT)
            registerReceiver(receiver, filter)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()

    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
