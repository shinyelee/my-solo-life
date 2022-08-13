package com.shinyelee.my_solo_life.utils

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

// 클래스 인스턴스 없이 클래스 내부에 접근하려면
class FBAuth {

    // -> 클래스 내부에 객체 선언할 때 동반 객체로 선언
    companion object {

        // FirebaseAuth의 인스턴스를 선언
        private lateinit var auth: FirebaseAuth

        // getUid(), getTime() -> 편의상 FBAuth.kt에 분리함

        // 현재 사용자 uid 받아옴
        fun getUid(): String {

            // .getInstance() -> 데이터베이스에 접근할 수 있는 진입점
            auth = FirebaseAuth.getInstance()

            // 현재 사용자 uid를 문자열로 반환
            return auth.currentUser?.uid.toString()

        }

        // 현재 시간 받아옴
        fun getTime(): String {

            // 캘린더 인스턴스 생성
            val currentDateTime = Calendar.getInstance().time

            // SimpleDateFormat() -> 사용자가 임의로 표기 형식 지정 가능
            // Locale.KOREA -> 지역설정 한국
            val dateFormat = SimpleDateFormat("yy-MM-dd HH:mm", Locale.KOREA).format(currentDateTime)

            // 원하는 포맷 및 한국어로 시간 반환
            return dateFormat

        }

    }

}