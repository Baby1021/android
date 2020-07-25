package com.laiyuanwen.android.baby.bean

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import java.io.Serializable

/**
 * Created by laiyuanwen on 2019-01-20.
 *
 * Love数据结构
 */
@Keep
data class Love(
        val id: Long,
        val content: String,
        val user: User,
        val created: String,
        val images: List<String>?,
        val comments: List<LoveComment>
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString().toString(),
            parcel.readParcelable(User::class.java.classLoader)!!,
            parcel.readString().toString(),
            parcel.createStringArrayList(),
            parcel.createTypedArrayList(LoveComment)!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(content)
        parcel.writeParcelable(user, flags)
        parcel.writeString(created)
        parcel.writeStringList(images)
        parcel.writeTypedList(comments)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Love> {
        override fun createFromParcel(parcel: Parcel): Love {
            return Love(parcel)
        }

        override fun newArray(size: Int): Array<Love?> {
            return arrayOfNulls(size)
        }
    }
}