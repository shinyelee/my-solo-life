package com.shinyelee.my_solo_life.board

import android.util.Log
import android.widget.ListAdapter
import android.widget.ListView

object ListHelper {

    // 댓글 개수에 따라 댓글 목록의 높이를 조정하기
    fun getListViewSize(cLV: ListView) {

        // 아무것도 하지 않고 null을 반환
        val cLVAdapter: ListAdapter = cLV.adapter
            ?:
            return

        // 댓글 목록 전체 높이
        var totalHeight = 0

        // 최종 크기를 얻기 위해 for문에 listAdapter 설정
        for (size in 0 until cLVAdapter.count) {
            val listItem = cLVAdapter.getView(size, null, cLV)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }

        // 어댑터 내에 리스트뷰의 아이템을 세팅
        val params = cLV.layoutParams
        params.height =
            totalHeight + cLV.dividerHeight * (cLVAdapter.count -1)
        cLV.layoutParams = params

        // 최종 높이 로그 출력
        Log.i("height of listItem:", totalHeight.toString())

    }

}