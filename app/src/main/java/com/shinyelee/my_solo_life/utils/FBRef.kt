package com.shinyelee.my_solo_life.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {

    private val TAG = "FBRef"

    companion object {

        // firebase
        private val database = Firebase.database

        // 카테고리
        val cate1 = database.getReference("contents")
        val cate2 = database.getReference("contents2")

        // 북마크
        val bookmarkRef = database.getReference("bookmark_list")

        // 게시판
        val boardRef = database.getReference("board")

        // 댓글
        val commentRef = database.getReference("comment")

    }

}