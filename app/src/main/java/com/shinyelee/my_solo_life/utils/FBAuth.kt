package com.shinyelee.my_solo_life.utils

import com.google.firebase.auth.FirebaseAuth

class FBAuth {

    // 클래스 인스턴스 없이 클래스 내부에 접근하려면
    // -> 클래스 내부에 객체 선언할 때 동반 객체로 선언
    companion object {

        // 파이어베이스 인스턴스 선언
        private lateinit var auth: FirebaseAuth

        // UID 리턴해주는 함수
        fun getUid(): String {
            auth = FirebaseAuth.getInstance()
            return auth.currentUser?.uid.toString()
        }

    }

}