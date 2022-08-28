package com.shinyelee.my_solo_life.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.view.isVisible
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.utils.FBAuth

// boardList -> 아이템(=게시글=제목+본문+uid+시간) 목록
class BoardLVAdapter(val boardList : MutableList<BoardModel>) : BaseAdapter() {

    // 아이템 총 개수 반환
    override fun getCount(): Int = boardList.size

    // 아이템 반환
    override fun getItem(position: Int): Any = boardList[position]

    // 아이템의 아이디 반환
    override fun getItemId(position: Int): Long = position.toLong()

    // 아이템을 표시할 뷰 반환
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView

        // 레이아웃 인플레이터 -> 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
        view = LayoutInflater.from(parent?.context).inflate(R.layout.board_lv_item, parent, false)

        // 각 아이템뷰의 제목/본문/시간 영역에
        val title = view?.findViewById<TextView>(R.id.titleArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)

        // 제목, 본문, 시간 넣음
        title!!.text = boardList[position].title
        time!!.text = boardList[position].time

        // 현재 사용자가 작성한 글만 따로 표시하기 위해
        val myBoardBadge = view?.findViewById<TextView>(R.id.myBoardBadge)

        // 게시글 작성자의 uid와 현재 사용자의 uid가 일치하면 배지가 보이도록 처리
        myBoardBadge?.isVisible = boardList[position].uid.equals(FBAuth.getUid())

        // 뷰 반환
        return view!!

    }

}