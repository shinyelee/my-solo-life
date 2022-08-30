package com.shinyelee.my_solo_life.comment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.utils.FBAuth
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.LinearLayout


// commentList -> 아이템(=댓글=본문+uid+시간) 목록
class CommentLVAdapter(val commentList : MutableList<CommentModel>) : BaseAdapter()  {

    // 아이템 총 개수 반환
    override fun getCount(): Int = commentList.size

    // 아이템 반환
    override fun getItem(position: Int): Any = commentList[position]

    // 아이템의 아이디 반환
    override fun getItemId(position: Int): Long = position.toLong()

    // 아이템을 표시할 뷰 반환
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView

        // 레이아웃 인플레이터 -> 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
        view = LayoutInflater.from(parent?.context).inflate(R.layout.comment_lv_item, parent, false)

        // 각 아이템뷰의 본문/시간 영역에
        val commentMain = view?.findViewById<TextView>(R.id.commentMainArea)
        val commentTime = view?.findViewById<TextView>(R.id.commentTimeArea)

        // 본문, 시간 넣음
        commentMain!!.text = commentList[position].main
        commentTime!!.text = commentList[position].time

        // 현재 사용자가 작성한 댓글만 따로 표시하기 위해
        val myCommentBadge = view?.findViewById<TextView>(R.id.myCommentBadge)

        // 댓글 작성자의 uid와 현재 사용자의 uid가 일치하면 배지가 보이도록 처리
        myCommentBadge?.isVisible = commentList[position].uid.equals(FBAuth.getUid())

        // 댓글 세팅 버튼도 마찬가지
        val commentSettingBtn = view?.findViewById<ImageView>(R.id.commentSettingBtn)
        commentSettingBtn?.isVisible = commentList[position].uid.equals(FBAuth.getUid())

//        // 클릭 리스너 직접 구현해서 달아줘야 함
//        commentSettingBtn?.setOnClickListener {
//            itemClickListener.onClick(it, position)
//        }

        // 뷰 반환
        return view!!

    }

//    // 클릭 리스너 만듦
//    interface OnItemClickListener {
//        fun onClick(v: View, position: Int)
//    }
//
//    // 외부에서 클릭 시 이벤트 설정
//    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
//        this.itemClickListener = onItemClickListener
//    }
//
//    // setItemClickListener로 설정한 함수 실행
//    private lateinit var itemClickListener : OnItemClickListener

}