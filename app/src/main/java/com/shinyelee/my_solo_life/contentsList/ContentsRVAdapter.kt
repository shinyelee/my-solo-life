package com.shinyelee.my_solo_life.contentsList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shinyelee.my_solo_life.R

class ContentsRVAdapter(val context : Context, val items : ArrayList<ContentsModel>) : RecyclerView.Adapter<ContentsRVAdapter.Viewholder>() {

    // item 하나 가져옴
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentsRVAdapter.Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent, false)
        return Viewholder(v)
    }

    // item 내용
    override fun onBindViewHolder(holder: ContentsRVAdapter.Viewholder, position: Int) {
        holder.bindItems(items[position])
    }

    // 전체 item 개수
    override fun getItemCount(): Int {
        return items.size
    }

    // item에 데이터 넣어줌
    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item : ContentsModel) {
            val contentsTitle = itemView.findViewById<TextView>(R.id.textArea)

            // glide
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)

            contentsTitle.text = item.title

            // glide
            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }
    }

}