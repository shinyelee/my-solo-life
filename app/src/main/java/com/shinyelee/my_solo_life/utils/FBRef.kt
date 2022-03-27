package com.shinyelee.my_solo_life.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {

    companion object {

        private val database = Firebase.database

        val cate1 = database.getReference("contents")
        val cate2 = database.getReference("contents2")

        val bookmarkRef = database.getReference("bookmark_list")

    }

}