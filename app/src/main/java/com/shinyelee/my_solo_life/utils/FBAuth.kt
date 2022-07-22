package com.shinyelee.my_solo_life.utils

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class FBAuth {

    private val TAG = "FBAuth"

    companion object {

        // firebase
        private lateinit var auth: FirebaseAuth

        // 내 UID 가져옴
        fun getUid() : String {
            auth = FirebaseAuth.getInstance()
            return auth.currentUser?.uid.toString()
        }

        // time stamp
        fun getTime() : String {
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(currentDateTime)
            return dateFormat
        }

    }

}