package com.xiyou.community.data

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Comments(
    val userInfo: UserInfo,
    val date: String,
    val comments: List<Comments>,

):Serializable, Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readParcelable<UserInfo>(Thread.currentThread().contextClassLoader)!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(CREATOR)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(userInfo, 0)
        parcel.writeString(date)
        parcel.writeTypedList(comments)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comments> {
        override fun createFromParcel(parcel: Parcel): Comments {
            return Comments(parcel)
        }

        override fun newArray(size: Int): Array<Comments?> {
            return arrayOfNulls(size)
        }
    }

}
data class UserInfo(
    val head: String,
    val name: String,
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(head)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfo> {
        override fun createFromParcel(parcel: Parcel): UserInfo {
            return UserInfo(parcel)
        }

        override fun newArray(size: Int): Array<UserInfo?> {
            return arrayOfNulls(size)
        }
    }

}