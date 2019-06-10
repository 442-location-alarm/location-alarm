package com.example.locationalarm

import android.app.Service
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.IBinder


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