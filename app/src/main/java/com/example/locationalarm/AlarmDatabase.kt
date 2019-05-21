package com.example.locationalarm

import androidx.room.Database
import androidx.room.RoomDatabase

/*
 * Instantiate as so:
 *  val db = Room.databaseBuilder(
 *          applicationContext,
 *          AlarmDatabase::class.java, "alarm-data"
 *      ).build()
 */
@Database(entities = arrayOf(AlarmData::class), version = 1)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDataDao(): AlarmDataDao
}