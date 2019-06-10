package com.example.locationalarm

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarms ORDER BY creation_date ASC")
    fun getAll(): LiveData<List<Alarm>>

    @Insert
    fun insert(alarm: Alarm)

    @Query("DELETE FROM alarms WHERE uid = :alarmId")
    fun delete(alarmId: String)

    @Update
    fun update(alarm: Alarm)
}