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
import androidx.core.app.NotificationManagerCompat

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

        var notificationManager: NotificationManager

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
            notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        } else {
            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }


        val intent = Intent(context, AlarmListActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        // TODO finish making notifications!
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("")
            .setContentText("")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, notification.build())
        }
    }
}
