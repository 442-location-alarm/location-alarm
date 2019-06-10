package com.example.locationalarm

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarms ORDER BY creation_date ASC")
    fun getAll(): LiveData<List<Alarm>>

    @Insert
    fun insert(alarm: Alarm)

    @Query("DELETE FROM alarms WHERE uid = :alarmID")
    fun delete(alarmID: String)

    @Query("UPDATE alarms SET name = :name WHERE uid = :alarmID")
    fun updateName(alarmID: String, name: String)

    @Query("UPDATE alarms SET radius = :radius WHERE uid = :alarmID")
    fun updateRadius(alarmID: String, radius: Double)

    @Query("UPDATE alarms SET alert = :alert WHERE uid = :alarmID")
    fun updateAlert(alarmID: String, alert: String)

    @Query("UPDATE alarms SET location = :location, latitude = :latitude, longitude = :longitude WHERE uid = :alarmID")
    fun updateLocation(alarmID: String, location: String, latitude: Double, longitude: Double)

    @Update
    fun update(alarm: Alarm)
}