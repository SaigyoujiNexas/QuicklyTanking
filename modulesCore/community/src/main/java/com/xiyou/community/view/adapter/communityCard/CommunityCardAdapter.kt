package com.xiyou.community.view.adapter.communityCard

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.xiyou.community.data.CommunityData

class CommunityCardAdapter(diffCallback: DiffUtil.ItemCallback<CommunityData>)
    : ListAdapter<CommunityData, CommunityCardViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityCardViewHolder {
        return CommunityCardViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CommunityCardViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item)
    }
}