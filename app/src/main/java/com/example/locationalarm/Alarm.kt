package com.example.locationalarm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@Entity(tableName = "alarms")
class Alarm(name: String, location: String, radius: Double, alert: String) {
    @PrimaryKey
    var uid: String = UUID.randomUUID().toString()

    var name: String = name

    var location: String = location

//    TODO: These should be instantiated once the Google Places API is complete
//    var latitude: Double
//
//    var longitude: Double


    var radius: Double = radius

    var alert: String = alert

    var active: Boolean = true

    @ColumnInfo(name = "creation_date")
    var creationDate: Instant = Instant.now()

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (javaClass != other.javaClass) {
            return false
        }

        val otherAlarm = other as Alarm

        // TODO: Add in lat/long once implemented
        return uid == otherAlarm.uid
                && name == otherAlarm.name
                && location == otherAlarm.location
                && radius == otherAlarm.radius
                && alert == otherAlarm.alert
                && active == otherAlarm.active
                && creationDate == otherAlarm.creationDate
    }

    // TODO: Add in latLng once implemented
    override fun hashCode(): Int {
        return Objects.hash(uid, name, location, radius, alert, active, creationDate)
    }

    fun enable() {
        // TODO: Joy add stuff here
        active = true
    }

    fun disable() {
        // TODO: Joy add stuff here
        active = false
    }
}