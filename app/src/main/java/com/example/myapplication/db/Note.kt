package com.example.myapplication.db

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    var title : String?,
    var text : String?,
    var color : Int = Color.argb(255, 255, 255, 255),
    var lastChanged : Long = System.currentTimeMillis(),
    val created : Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id : Int? = null,
    var isDone : Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeInt(color)
        parcel.writeLong(lastChanged)
        parcel.writeLong(created)
        parcel.writeValue(id)
        parcel.writeByte(if (isDone) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}