package com.shinyelee.my_solo_life.contentsList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shinyelee.my_solo_life.R

class ContentsRVAdapter(val items : ArrayList<String>) : RecyclerView.Adapter<ContentsRVAdapter.Viewholder>() {

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
        fun bindItems(item : String) {

        }
    }

}