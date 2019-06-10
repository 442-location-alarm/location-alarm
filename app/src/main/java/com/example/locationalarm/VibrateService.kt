package com.example.locationalarm

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Vibrator

class VibrateService : Service() {

    private val vibrate = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val pattern = longArrayOf(0, 100, 1000)
        vibrate!!.vibrate(pattern, 0)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        vibrate!!.cancel()
    }
}
