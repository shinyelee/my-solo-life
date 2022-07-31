package com.shinyelee.my_solo_life.contentsList

// 콘텐츠 데이터를 string이 아닌 모델로 만듦
data class ContentsModel (

    // 제목
    var title : String = "",

    // 이미지 URL
    var imageUrl : String = "",

    // 웹 URL
    var webUrl : String = ""

)