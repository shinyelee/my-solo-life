package com.shinyelee.my_solo_life.contentsList

import android.content.Context
import android.content.Intent
import android.util.Log
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

// 리사이클러뷰의 어댑터
// -> RecyclerView.Adapter를 상속해서 구현
class ContentsRVAdapter(
    val context: Context,
    val items: ArrayList<ContentsModel>,
    val keyList: ArrayList<String>,
    val bookmarkIdList: MutableList<String>
): RecyclerView.Adapter<ContentsRVAdapter.Viewholder>() {

    // 뷰홀더 객체 생성 및 초기화
    // 아직 데이터는 들어가있지 않은 상태
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentsRVAdapter.Viewholder {

        // 레이아웃 인플레이터
        // -> 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent, false)

        // 게시글의 키 리스트, 북마크 ID 리스트 로그로 출력
        Log.d("ContentsRVAdapter", keyList.toString())
        Log.d("ContentsRVAdapter", bookmarkIdList.toString())

        // 뷰홀더 리턴
        return Viewholder(v)

    }

    // 뷰홀더 객체와 데이터를 연결함
    // 리스트에 출력할 데이터를 불러와 뷰홀더의 레이아웃에 데이터를 넣어줌
    override fun onBindViewHolder(holder: ContentsRVAdapter.Viewholder, position: Int) {

        // 데이터 매핑
        holder.bindItems(items[position], keyList[position])

    }

    // 아이템(개별 데이터) 총 개수 반환
    override fun getItemCount(): Int = items.size

    // 아이템에 데이터 넣어줌
    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        // 데이터 매핑(게시글 정보, 키 값)
        fun bindItems(item: ContentsModel, key: String) {


            // 리사이클러뷰는 setOnItemClickListener 없음
            // -> 개발자가 직접 구현해야 함

            // 클릭 리스너
            itemView.setOnClickListener {

                // 명시적 인텐트
                // -> 다른 액티비티 호출
                val intent = Intent(context, ContentsShowActivity::class.java)

                // 게시글에 해당하는 URL 넘겨줌
                intent.putExtra("url", item.webUrl)

                // 컨텐츠쇼 액티비티 시작
                itemView.context.startActivity(intent)

            }

            // 게시글 제목, 썸네일, 북마크(하트)
            val contentsTitle = itemView.findViewById<TextView>(R.id.titleArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            // 북마크 여부 표시
            // -> 북마크 ID 리스트가 게시글 키 정보를 포함하면
            if(bookmarkIdList.contains(key)) {

                // 주황색 하트
                bookmarkArea.setImageResource(R.drawable.bookmark56)

            // 포함하지 않으면
            } else {

                // 하얀색 하트
                bookmarkArea.setImageResource(R.drawable.bookmark56w)

            }

            // 하트를 클릭하면
            bookmarkArea.setOnClickListener {

                // bookmark_list 하위에 사용자 UID별로 나눠 게시글의 키 값을 저장해야 함

                // 이미 북마크 된 상태
                if(bookmarkIdList.contains(key)) {

                    // -> 북마크 삭제
                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(key)
                        .removeValue()

                // 아직 북마크 안 된 상태
                } else {

                    // -> 북마크 저장
                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(key)
                        .setValue(BookmarkModel(true))

                }

            }

            // 제목
            contentsTitle.text = item.title

            // 썸네일
            // -> 글라이드를 이용해 imageUrl에 있는 이미지를 imageViewArea에 집어넣음
            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }

    }

}