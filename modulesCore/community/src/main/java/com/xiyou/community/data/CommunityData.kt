package com.xiyou.community.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommunityData(
    val author: String,
    val content: String,
    val time: String,
    val avatar: String,
    val picture: List<Picture>,
    val reContent: String,
    val reAuthor: String,
    val rePicture: List<Picture>
) : Parcelable

@Parcelize
data class Picture(val image: String): Parcelable