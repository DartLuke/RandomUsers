package com.danielpasser.randomusers.models

import android.os.Parcel
import android.os.Parcelable


data class Dob(val date: String?) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Dob> {
        override fun createFromParcel(parcel: Parcel): Dob {
            return Dob(parcel)
        }

        override fun newArray(size: Int): Array<Dob?> {
            return arrayOfNulls(size)
        }
    }
}
