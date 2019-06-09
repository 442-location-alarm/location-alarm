package com.example.locationalarm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration



@Database(entities = arrayOf(Alarm::class), version = 2, exportSchema = false)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    companion object {
        @Volatile
        private var INSTANCE: AlarmDatabase? = null

        fun getInstance(context: Context): AlarmDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AlarmDatabase::class.java, "app.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}