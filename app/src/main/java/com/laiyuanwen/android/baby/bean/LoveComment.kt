package com.laiyuanwen.android.baby.bean

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by laiyuanwen on 2019-02-04.
 */
data class LoveComment(
        val id: Int,
        val userId: String,
        val content: String,
        val loveId: Int,
        val createTime: Long,
        val user: User
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readParcelable(User::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(userId)
        parcel.writeString(content)
        parcel.writeInt(loveId)
        parcel.writeLong(createTime)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoveComment> {
        override fun createFromParcel(parcel: Parcel): LoveComment {
            return LoveComment(parcel)
        }

        override fun newArray(size: Int): Array<LoveComment?> {
            return arrayOfNulls(size)
        }
    }
}