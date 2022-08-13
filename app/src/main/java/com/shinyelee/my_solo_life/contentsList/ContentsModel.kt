package com.shinyelee.my_solo_life.contentsList

// 블로그 컨텐츠에 대한 정보를 데이터 모델 형태로 묶음
data class ContentsModel (

    // 블로그 컨텐츠의 제목
    var title : String = "",

    // 블로그 컨텐츠의 썸네일 이미지 url
    var imageUrl : String = "",

    // 블로그 컨텐츠의 본문 url
    var webUrl : String = ""

)