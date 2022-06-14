package com.xiyou.community.view.adapter.questionAnswer

import androidx.recyclerview.widget.DiffUtil
import com.xiyou.community.data.QuestionAnswer

class QuestionAnswerDiffCallback: DiffUtil.ItemCallback<QuestionAnswer>() {
    override fun areItemsTheSame(oldItem: QuestionAnswer, newItem: QuestionAnswer): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: QuestionAnswer, newItem: QuestionAnswer): Boolean {
        return newItem.id == oldItem.id
    }
}