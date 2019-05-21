package com.example.locationalarm

import androidx.room.*

@Dao
interface AlarmDataDao {
    @Query("SELECT * FROM alarmData")
    fun getAll(): List<AlarmData>

    @Query("SELECT * FROM alarmData WHERE uid = :alarmId")
    fun getById(alarmId: Int): AlarmData

    @Insert
    fun insert(alarm: AlarmData)

    @Delete
    fun delete(alarm: AlarmData)

    @Update
    fun update(alarm: AlarmData)
}