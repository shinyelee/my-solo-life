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

// 리사이클러 뷰의 어댑터
// -> RecyclerView.Adapter를 상속해서 구현
class ContentsRVAdapter(val context: Context, val items: ArrayList<ContentsModel>): RecyclerView.Adapter<ContentsRVAdapter.Viewholder>() {

    // 뷰홀더 객체 생성 및 초기화
    // 아직 데이터는 들어가있지 않은 상태
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentsRVAdapter.Viewholder {

        // 레이아웃 인플레이터
        // -> 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent, false)
        return Viewholder(v)

    }

    // 뷰홀더 객체와 데이터를 연결함
    // 리스트에 출력할 데이터를 불러와 뷰홀더의 레이아웃에 데이터를 넣어줌
    override fun onBindViewHolder(holder: ContentsRVAdapter.Viewholder, position: Int) = holder.bindItems(items[position])

    // 아이템(개별 데이터) 총 개수 반환
    override fun getItemCount(): Int = items.size

    // 아이템에 데이터 넣어줌
    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        // 데이터 매핑
        fun bindItems(item : ContentsModel) {

            // 게시글 제목을 titleArea에 넣어줌
            val contentsTitle = itemView.findViewById<TextView>(R.id.titleArea)
            contentsTitle.text = item.title

            // 게시글 썸네일은
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)

            // 글라이드를 이용해 imageUrl에 있는 이미지를 imageViewArea에 집어넣음
            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }

    }

}