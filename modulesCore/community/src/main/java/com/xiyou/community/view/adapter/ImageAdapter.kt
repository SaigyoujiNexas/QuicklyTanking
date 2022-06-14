package com.xiyou.community.view.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xiyou.community.R
import com.xiyou.community.data.CommunityData
import com.xiyou.community.data.Picture

class ImageAdapter(diff: ImageDiffCallback): ListAdapter<Picture, ImageHolder>(diff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(getItem(position).image)
    }
}
class ImageHolder
    private constructor(val itemView: View): RecyclerView.ViewHolder(itemView){
    private val image: ImageView = itemView.findViewById<ImageView>(R.id.iv_image)

    fun bind(imageStr: String){
        val decoded = Base64.decode(imageStr, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)
        image.setImageBitmap(bitmap)
    }
    companion object{
        fun create(parent: ViewGroup): ImageHolder{
            val inflater = LayoutInflater.from(parent.context)
            val v= inflater.inflate(R.layout.item_image, parent, false)
            return ImageHolder(v)
        }
    }
}
class ImageDiffCallback(): DiffUtil.ItemCallback<Picture>(){
    override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
        return newItem === oldItem;
    }

    override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
        return newItem == oldItem
    }
}