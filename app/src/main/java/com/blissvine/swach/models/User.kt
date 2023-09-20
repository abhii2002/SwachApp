package com.blissvine.swach.models

import android.os.Parcel
import android.os.Parcelable

class User  (
    val id: String  = " ",
    val name: String  = "",
    val email: String = "",
    val image: String = "",
    val mobile: Long  = 0,
    val gender: String = "",
    val fcmToken: String = "",
    val profileCompleted: Int = 0,
    var  selected: Boolean = false,


    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!
    ) {
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest)  {
        writeString(id)
        writeString(name)
        writeString(email)
        writeString(image)
        writeLong(mobile)
        writeString(gender)
        writeString(fcmToken)
        writeInt(profileCompleted)
    }



    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}