package com.shinyelee.my_solo_life.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {

    // 클래스 인스턴스 없이 클래스 내부에 접근하려면
    // -> 클래스 내부에 객체 선언할 때 동반 객체로 선언
    companion object {

        // 파이어베이스
        private val database = Firebase.database

        // 파이어베이스의 bookmark_list에서 데이터 가져옴
        val bookmarkRef = database.getReference("bookmark_list")

    }

}