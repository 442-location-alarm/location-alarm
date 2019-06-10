package com.example.locationalarm

import android.app.Service
import android.app.Service.START_NOT_STICKY
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat.getExtras
import android.content.Intent
import android.os.IBinder
import android.media.Ringtone
import android.net.Uri


class RingtonePlayingService : Service() {
    private var ringtone: Ringtone? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        this.ringtone = RingtoneManager.getRingtone(this, alarmUri)
        ringtone!!.play()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        ringtone!!.stop()
    }
}