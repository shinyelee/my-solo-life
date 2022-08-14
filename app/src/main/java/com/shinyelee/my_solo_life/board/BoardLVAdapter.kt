package com.shinyelee.my_solo_life.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.shinyelee.my_solo_life.R

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

        if(view == null) {

            // 레이아웃 인플레이터 -> 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
            view = LayoutInflater.from(parent?.context).inflate(R.layout.board_lv_item, parent, false)

        }

        return view!!
    }

}