package com.xiyou.community.data

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
data class QuestionCard(
    val head: String,
    val title: String,
    val comment: String,
    val date: String,
    val user: String,
    val id: Int,
    val solved: Boolean,
    val answer: Array<QuestionAnswer?>
) :Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestionCard

        if (head != other.head) return false
        if (title != other.title) return false
        if (comment != other.comment) return false
        if (date != other.date) return false
        if (user != other.user) return false
        if (id != other.id) return false
        if (solved != other.solved) return false
        if (!answer.contentEquals(other.answer)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = head.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + id
        result = 31 * result + solved.hashCode()
        result = 31 * result + answer.contentHashCode()
        return result
    }
}


@Parcelize
data class QuestionAnswer(
    val head: String,
    val name: String,
    val id: Int,
    val date: String,
    val content: String
): Parcelable