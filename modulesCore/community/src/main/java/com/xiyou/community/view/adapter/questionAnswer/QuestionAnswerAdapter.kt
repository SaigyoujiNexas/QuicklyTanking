package com.xiyou.community.view.adapter.questionAnswer

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.xiyou.community.data.QuestionAnswer

class QuestionAnswerAdapter(callback: QuestionAnswerDiffCallback)
    : ListAdapter<QuestionAnswer, QuestionAnswerViewHolder>(callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionAnswerViewHolder {
        return QuestionAnswerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: QuestionAnswerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}