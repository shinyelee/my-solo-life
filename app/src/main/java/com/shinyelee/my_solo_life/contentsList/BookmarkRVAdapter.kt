package com.shinyelee.my_solo_life.contentsList

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef

class BookmarkRVAdapter(val context : Context,
                        val items : ArrayList<ContentsModel>,
                        val keyList : ArrayList<String>,
                        val bookmarkIdList : MutableList<String>)
    : RecyclerView.Adapter<BookmarkRVAdapter.Viewholder>() {

    // item 하나 가져옴
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkRVAdapter.Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent, false)
        Log.d("BookmarkRVAdapter", keyList.toString())
        Log.d("BookmarkRVAdapter", bookmarkIdList.toString())
        return Viewholder(v)
    }

    // item 내용
    override fun onBindViewHolder(holder: BookmarkRVAdapter.Viewholder, position: Int) {
        holder.bindItems(items[position], keyList[position])
    }

    // 전체 item 개수
    override fun getItemCount(): Int {
        return items.size
    }

    // item에 데이터 넣어줌
    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item : ContentsModel, key : String) {

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

            if(bookmarkIdList.contains(key)) {
                bookmarkArea.setImageResource(R.drawable.bookmark_color)
            } else {
                bookmarkArea.setImageResource(R.drawable.bookmark_white)
            }

            contentsTitle.text = item.title

            // glide
            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }

    }

}