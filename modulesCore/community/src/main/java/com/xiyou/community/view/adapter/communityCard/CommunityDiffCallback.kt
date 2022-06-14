package com.xiyou.community.view.adapter.communityCard

import androidx.recyclerview.widget.DiffUtil
import com.xiyou.community.data.CommunityData
import com.xiyou.community.data.QuestionCard

class CommunityDiffCallback(): DiffUtil.ItemCallback<CommunityData>() {
    override fun areItemsTheSame(oldItem: CommunityData, newItem: CommunityData): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: CommunityData, newItem: CommunityData): Boolean {
        return oldItem == newItem
    }
}