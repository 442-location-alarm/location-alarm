package com.example.locationalarm

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarms ORDER BY creation_date ASC")
    fun getAll(): LiveData<List<Alarm>>

    @Query("SELECT * FROM alarms WHERE uid = :alarmId")
    fun getById(alarmId: Int): Alarm

    @Insert
    fun insert(alarm: Alarm)

    @Delete
    fun delete(alarm: Alarm)

    @Update
    fun update(alarm: Alarm)
}