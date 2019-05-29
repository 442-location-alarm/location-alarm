package com.example.locationalarm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors



class AlarmViewModel(application: Application) : AndroidViewModel(application) {


    val alarmDao: AlarmDao = AlarmDatabase.getInstance(application).alarmDao()
    val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAll() : LiveData<List<Alarm>> {
        return alarmDao.getAll()
    }

    fun saveAlarm(alarm: Alarm) {
        executorService.execute { alarmDao.insert(alarm) }
    }

    fun updateAlarm(alarm: Alarm) {
        executorService.execute { alarmDao.update(alarm) }
    }

    fun deleteAlarm(alarm: Alarm) {
        executorService.execute { alarmDao.delete(alarm) }
    }
}