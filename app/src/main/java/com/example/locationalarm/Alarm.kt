package com.example.locationalarm

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*
import androidx.room.Ignore


@Entity(tableName = "alarms")
class Alarm(name: String, location: String, radius: Double, alert: String) : Parcelable {

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

    @JvmField
    @Ignore
    val CREATOR: Parcelable.Creator<Alarm> = object : Parcelable.Creator<Alarm> {
        override fun newArray(size: Int): Array<Alarm?> {
            return arrayOfNulls(size)
        }

        override fun createFromParcel(source: Parcel): Alarm {
            return Alarm(source)
        }
    }

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

    // TODO: Add lat and long
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(uid)
        dest.writeString(name)
        dest.writeString(location)
        dest.writeDouble(radius)
        dest.writeString(alert)
        dest.writeInt(if (active) 1 else 0)
        dest.writeLong(creationDate.toEpochMilli())
    }

    override fun describeContents(): Int {
        return 0
    }

    private constructor(uid: String, name: String, location: String, radius: Double, alert: String, active: Boolean,
                        creationDate: Instant) : this(name, location, radius, alert) {
        this.uid = uid
        this.active = active
        this.creationDate = creationDate
    }

    private constructor(source: Parcel) : this(source.readString()!!, source.readString()!!, source.readString()!!,
        source.readDouble(), source.readString()!!, source.readInt() != 0, Instant.ofEpochMilli(source.readLong()))


}