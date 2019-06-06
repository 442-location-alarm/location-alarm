package com.example.locationalarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

class ProximityIntentReceiver : BroadcastReceiver() {
    private val NOTIFICATION_ID = 1000
    private val CHANNEL_ID = "LocationAlarm"

    override fun onReceive(context: Context, intent: Intent) {

        val key = LocationManager.KEY_PROXIMITY_ENTERING

        val entering = intent.extras.getBoolean(key, false)

        if (entering) {
            Log.d(javaClass.simpleName, "entering")
        } else {
            Log.d(javaClass.simpleName, "exiting")
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = Resources.getSystem().getString(R.string.channel_name)
            val descriptionText = Resources.getSystem().getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, null, 0)

        // TODO finish making notifications!
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)


    }
}
