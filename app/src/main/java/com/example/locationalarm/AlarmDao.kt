package com.example.locationalarm

import androidx.room.*

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarms")
    fun getAll(): List<Alarm>

    @Query("SELECT * FROM alarms WHERE uid = :alarmId")
    fun getById(alarmId: Int): Alarm

    @Insert
    fun insert(alarm: Alarm)

    @Delete
    fun delete(alarm: Alarm)

    @Update
    fun update(alarm: Alarm)
}