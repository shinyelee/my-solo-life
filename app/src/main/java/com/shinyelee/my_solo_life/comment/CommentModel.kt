package com.shinyelee.my_solo_life.comment

// 게시글에 대한 정보를 데이터 모델 형태로 묶음
data class CommentModel (

    // 댓글 내용
    var main : String = "",

    // 작성자 uid
    var uid : String = "",

    // 작성 시간
    var time : String = ""

)