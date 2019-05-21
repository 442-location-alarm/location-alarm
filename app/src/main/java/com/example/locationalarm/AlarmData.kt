package com.example.locationalarm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class AlarmData(
    @PrimaryKey val uid: Int,
    val name: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Double,
    val alert: String,
    val active: Boolean,
    @ColumnInfo(name = "creation_date") val creationDate: LocalDateTime
)