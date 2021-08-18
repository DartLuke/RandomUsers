package com.danielpasser.randomusers.models

import android.os.Parcel
import android.os.Parcelable


data class User(val name: Name?, val email: String?, val picture: Picture?, val dob: Dob?) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Name::class.java.classLoader),
        parcel.readString(),
        parcel.readParcelable(Picture::class.java.classLoader),
        parcel.readParcelable(Dob::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(name, flags)
        parcel.writeString(email)
        parcel.writeParcelable(picture, flags)
        parcel.writeParcelable(dob, flags)
    }

    override fun describeContents(): Int {
        return 0
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
