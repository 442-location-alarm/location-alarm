package com.example.locationalarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Vibrator

class CancelAlarmReceiver : BroadcastReceiver() {
    private val NOTIFICATION_ID = 1000


    override fun onReceive(context: Context, intent: Intent) {

        var notificationManager: NotificationManager

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "LocationAlarm"
            val descriptionText = "Proximity alert for when you are in the radius of a desired location."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(ProximityIntentReceiver.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        } else {
            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val vibrate = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?

        val stopIntent = Intent(context, RingtonePlayingService::class.java)
        context.stopService(stopIntent)
        vibrate!!.cancel()

        notificationManager.cancel(NOTIFICATION_ID)
    }
}
