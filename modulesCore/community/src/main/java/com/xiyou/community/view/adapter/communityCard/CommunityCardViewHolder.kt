package com.xiyou.community.view.adapter.communityCard

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.xiyou.community.R
import com.xiyou.community.data.CommunityData

class CommunityCardViewHolder(val itemView: View):RecyclerView.ViewHolder(itemView) {
    private val head: ImageView = itemView.findViewById(R.id.iv_community_head)
    private val name: TextView = itemView.findViewById(R.id.tv_community_name)
    private val title: TextView = itemView.findViewById(R.id.tv_community_content)
    private val date : TextView = itemView.findViewById(R.id.tv_community_date)
    fun bind (item: CommunityData)
    {
        val bundle = bundleOf("content" to item)
        val byte = Base64.decode(item.avatar, Base64.DEFAULT)
        head.setImageBitmap(BitmapFactory.decodeByteArray(byte, 0, byte.size))

        name.text = item.author
        title.text = item.content
        date.text = item.time
        itemView.setOnClickListener(Navigation
            .createNavigateOnClickListener(R.id.action_community_to_community_content, bundle))
    }
    companion object{
        fun create(parent: ViewGroup): CommunityCardViewHolder {
            val v = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_cummunity_card, parent, false)
            return CommunityCardViewHolder(v)
        }
    }
}