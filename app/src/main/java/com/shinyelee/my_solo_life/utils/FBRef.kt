package com.shinyelee.my_solo_life.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// 클래스 인스턴스 없이 클래스 내부에 접근하려면
class FBRef {

    // -> 클래스 내부에 객체 선언할 때 동반 객체로 선언
    companion object {

        // 파이어베이스
        private val database = Firebase.database

        // .getReference() -> 데이터베이스의 루트 폴더 주소 값을 반환

        // 블로그 카테고리
        val androidStudio = database.getReference("android_studio")
        val kotlinSyntax = database.getReference("kotlin_syntax")
        val errorWarning = database.getReference("error_warning")
        val vcsGithub = database.getReference("vcs_github")
        val webInternet = database.getReference("web_internet")

        // 북마크 목록
        val bookmarkRef = database.getReference("bookmark_list")

        // 게시글
        val boardRef = database.getReference("board")

        // 댓글
        val commentRef = database.getReference("comment")

    }

}