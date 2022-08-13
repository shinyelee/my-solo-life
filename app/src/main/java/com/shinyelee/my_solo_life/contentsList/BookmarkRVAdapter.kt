package com.shinyelee.my_solo_life.contentsList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef

class BookmarkRVAdapter(
    val context: Context, // 컨텍스트
    val items: ArrayList<ContentsModel>, // 아이템(=컨텐츠=제목+썸네일+본문) 목록
    val keyList: ArrayList<String>, // 아이템의 키 목록
    val bookmarkIdList: MutableList<String> // 북마크의 아이디(키) 목록
): RecyclerView.Adapter<BookmarkRVAdapter.Viewholder>() {
// 리사이클러뷰의 어댑터 -> RecyclerView.Adapter를 상속해서 구현

    // 뷰홀더 객체 생성 및 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkRVAdapter.Viewholder {

        // 레이아웃 인플레이터 -> 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent, false)

        // 아직 데이터는 들어가있지 않은 껍데기
        return Viewholder(v)

    }

    // 뷰홀더 객체와 데이터를 연결
    override fun onBindViewHolder(holder: BookmarkRVAdapter.Viewholder, position: Int) {

        // 껍데기(뷰홀더의 레이아웃)에 출력할 내용물(아이템 목록, 아이템의 키 목록)을 넣어줌
        holder.bindItems(items[position], keyList[position])

    }

    // 아이템들의 총 개수 반환
    override fun getItemCount(): Int = items.size

    // 각 아이템에 데이터 넣어줌
    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        // 데이터 매핑(아이템, 아이템의 키)
        fun bindItems(item: ContentsModel, key: String) {

            // 리사이클러뷰는 setOnItemClickListener 없음 -> 개발자가 직접 구현해야 함

            // 아이템뷰(아이템 영역)를 클릭하면
            itemView.setOnClickListener {

                // 명시적 인텐트 -> 다른 액티비티 호출
                val intent = Intent(context, ContentsShowActivity::class.java)

                // 해당 아이템의 본문 url을 전달
                intent.putExtra("url", item.webUrl)

                // 컨텐츠쇼 액티비티 시작(웹뷰)
                itemView.context.startActivity(intent)

            }

            // 각 아이템뷰의 제목/썸네일/북마크(하트) 영역
            val contentsTitle = itemView.findViewById<TextView>(R.id.titleArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            // -> 북마크아이디리스트(=북마크된 아이템의 키 목록)에 화면에 표시된 아이템의 키 정보가 포함되면
            if(bookmarkIdList.contains(key)) {

                // 해당 아이템의 하트 -> 주황색
                bookmarkArea.setImageResource(R.drawable.bookmark56)

            // 포함되지 않으면
            } else {

                // 하트 -> 하얀색
                bookmarkArea.setImageResource(R.drawable.bookmark56w)

            }

            // 하트 클릭하면
            bookmarkArea.setOnClickListener {

                // -> 북마크 삭제
                FBRef.bookmarkRef
                    .child(FBAuth.getUid())
                    .child(key)
                    .removeValue()

            }

            // 아이템의 제목 -> titleArea에 넣음
            contentsTitle.text = item.title

            // 아이템의 썸네일 -> 글라이드로 썸네일 이미지의 url을 imageViewArea에 넣음
            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }

    }

}