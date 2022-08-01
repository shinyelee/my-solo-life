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

// 리사이클러뷰에서 어댑터는 RecyclerView.Adapter를 상속해서 구현
class ContentsRVAdapter(val context : Context,
                        val items : ArrayList<ContentsModel>,
                        val keyList : ArrayList<String>,
                        val bookmarkIdList : MutableList<String>)
    : RecyclerView.Adapter<ContentsRVAdapter.Viewholder>() {

    // 뷰홀더 객체 생성 및 초기화
    // 아직 데이터는 들어가있지 않은 상태
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentsRVAdapter.Viewholder {

        // 레이아웃 인플레이터
        // 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent, false)

//        Log.d("ContentsRVAdapter", keyList.toString())
//        Log.d("ContentsRVAdapter", bookmarkIdList.toString())

        return Viewholder(v)

    }

    // 뷰홀더 객체와 데이터를 연결함
    // 리스트에 출력할 데이터를 불러와 뷰홀더의 레이아웃에 데이터를 넣어줌
    override fun onBindViewHolder(holder: ContentsRVAdapter.Viewholder, position: Int) = holder.bindItems(items[position], keyList[position])

    // 아이템 총 개수 반환
    override fun getItemCount(): Int = items.size

    // 아이템에 데이터 넣어줌
    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        // 데이터 매핑
        fun bindItems(item : ContentsModel, key : String) {

            // 아이템 클릭하면
            itemView.setOnClickListener {

//                Toast.makeText(context, item.title, Toast.LENGTH_LONG).show()

                // 컨텐츠쇼 액티비티 실행
                val intent = Intent(context, ContentsShowActivity::class.java)
                intent.putExtra("url", item.webUrl)
                itemView.context.startActivity(intent)

            }

            // 게시글 제목
            val contentsTitle = itemView.findViewById<TextView>(R.id.textArea)

            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            if(bookmarkIdList.contains(key)) {
                bookmarkArea.setImageResource(R.drawable.bookmark_color)
            } else {
                bookmarkArea.setImageResource(R.drawable.bookmark_white)
            }

            bookmarkArea.setOnClickListener {
                Log.d("ContentsRVAdapter", FBAuth.getUid())
                Toast.makeText(context, key, Toast.LENGTH_LONG).show()

                if(bookmarkIdList.contains(key)) {
                    // 북마크 (O)
                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(key)
                        .removeValue()
                } else {
                    // 북마크 (X)
                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(key)
                        .setValue(BookmarkModel(true))
                }

            }

            contentsTitle.text = item.title

            // 글라이드
            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }

    }

}