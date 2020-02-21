package com.laiyuanwen.android.baby.bean

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import java.io.Serializable

/**
 * Created by laiyuanwen on 2018/12/31.
 */

@Keep
data class User(
        val userId: String,
        val avatar: String,
        val name: String
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(avatar)
        parcel.writeString(name)
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