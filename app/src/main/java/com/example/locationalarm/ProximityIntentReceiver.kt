package com.example.locationalarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.location.LocationManager
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.os.VibrationEffect
import androidx.core.content.ContextCompat.getSystemService
import android.os.Vibrator



class ProximityIntentReceiver : BroadcastReceiver() {
    private val NOTIFICATION_ID = 1000

    companion object {
        val CHANNEL_ID = "LocationAlarm"
    }

    override fun onReceive(context: Context, intent: Intent) {

        val key = LocationManager.KEY_PROXIMITY_ENTERING

        val entering = intent.extras.getBoolean(key, false)

        if (entering) {
            Log.d(javaClass.simpleName, "entering")
        } else {
            Log.d(javaClass.simpleName, "exiting")
        }
        val startRingIntent = Intent(context, RingtonePlayingService::class.java)
        Log.d("IntentReceiver", intent.toString())

        val startVibrateIntent = Intent(context, VibrateService::class.java)

        if (intent.hasExtra("alert")) {
            val alert = intent.extras.getString("alert")
            if (alert.equals("sound")) {
                context.startService(startRingIntent)
            } else {
                context.startService(startVibrateIntent)
            }
        }

        val intent = Intent(context, AlarmListActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val cancelAlarmIntent = Intent(context, CancelAlarmReceiver::class.java)
        val cancelAlarmPendingIntent = PendingIntent.getBroadcast(context, 0, cancelAlarmIntent, 0)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("You're almost there!")
            .setContentText("You are in the radius of your destination")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            //.setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_snooze, "Stop Alarm", cancelAlarmPendingIntent)

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, notification.build())
        }
    }
}
