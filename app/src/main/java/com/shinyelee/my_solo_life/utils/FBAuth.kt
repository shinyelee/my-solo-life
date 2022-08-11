package com.shinyelee.my_solo_life.utils

import com.google.firebase.auth.FirebaseAuth

// 클래스 인스턴스 없이 클래스 내부에 접근하려면
class FBAuth {

    // -> 클래스 내부에 객체 선언할 때 동반 객체로 선언
    companion object {

        // FirebaseAuth의 인스턴스를 선언
        private lateinit var auth: FirebaseAuth

        // 편의상 FBAuth.kt에 분리함
        fun getUid(): String {

            // .getInstance() -> 데이터베이스에 접근할 수 있는 진입점
            auth = FirebaseAuth.getInstance()

            // 현재 사용자 uid를 문자열로 반환
            return auth.currentUser?.uid.toString()

        }

    }

}