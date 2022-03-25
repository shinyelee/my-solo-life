package com.shinyelee.my_solo_life.contentsList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shinyelee.my_solo_life.R

class ContentsRVAdapter(val context : Context, val items : ArrayList<ContentsModel>) : RecyclerView.Adapter<ContentsRVAdapter.Viewholder>() {

    // item 하나 가져옴
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentsRVAdapter.Viewholder {
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

            itemView.setOnClickListener {
                Toast.makeText(context, item.title, Toast.LENGTH_LONG).show()
                val intent = Intent(context, ContentsShowActivity::class.java)
                intent.putExtra("url", item.webUrl)
                itemView.context.startActivity(intent)
            }

            val contentsTitle = itemView.findViewById<TextView>(R.id.textArea)
            // glide
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            bookmarkArea.setOnClickListener {
                Toast.makeText(context, "북마크 클릭됨", Toast.LENGTH_LONG).show()
            }

            contentsTitle.text = item.title

            // glide
            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }

    }

}