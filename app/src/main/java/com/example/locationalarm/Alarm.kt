package com.example.locationalarm

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "alarms")
class Alarm(name: String, location: String, radius: Double, alert: String, latitude: Double, longitude: Double) : Parcelable {

    @PrimaryKey
    var uid: String = UUID.randomUUID().toString()

    var name: String = name

    var location: String = location

    var latitude: Double = latitude

    var longitude: Double = longitude


    var radius: Double = radius

    var alert: String = alert

    var active: Boolean = true

    @ColumnInfo(name = "creation_date")
    var creationDate: Long = System.currentTimeMillis()

    companion object CREATOR: Parcelable.Creator<Alarm> {
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

        return uid == otherAlarm.uid
                && name == otherAlarm.name
                && location == otherAlarm.location
                && radius == otherAlarm.radius
                && alert == otherAlarm.alert
                && active == otherAlarm.active
                && creationDate == otherAlarm.creationDate
                && latitude == otherAlarm.latitude
                && longitude == otherAlarm.longitude
    }

    override fun hashCode(): Int {
        return Objects.hash(uid, name, location, radius, alert, active, creationDate, latitude, longitude)
    }

    fun enable() {
        // TODO: Joy add stuff here
        active = true
    }

    fun disable() {
        // TODO: Joy add stuff here
        active = false
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(uid)
        dest.writeString(name)
        dest.writeString(location)
        dest.writeDouble(latitude)
        dest.writeDouble(longitude)
        dest.writeDouble(radius)
        dest.writeString(alert)
        dest.writeInt(if (active) 1 else 0)
        dest.writeLong(creationDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    private constructor(uid: String, name: String, location: String , latitude: Double, longitude: Double,
                        radius: Double, alert: String, active: Boolean, creationDate: Long) :
                        this(name, location, radius, alert, latitude, longitude) {
        this.uid = uid
        this.active = active
        this.creationDate = creationDate
    }

    private constructor(source: Parcel) : this(source.readString()!!, source.readString()!!, source.readString()!!,
        source.readDouble(), source.readDouble(), source.readDouble(), source.readString()!!,
        source.readInt() != 0, source.readLong())


}