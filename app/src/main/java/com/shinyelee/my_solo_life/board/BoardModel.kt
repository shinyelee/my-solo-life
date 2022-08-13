package com.shinyelee.my_solo_life.board

// 게시글에 대한 정보를 데이터 모델 형태로 묶음
data class BoardModel (

    // 게시글 제목
    var title : String = "",

    // 게시글 본문
    var main : String = "",

    // 작성자 uid
    var uid : String = "",

    // 작성 시간
    var time : String = ""

)