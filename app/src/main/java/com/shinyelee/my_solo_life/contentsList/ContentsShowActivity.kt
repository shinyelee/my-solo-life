package com.shinyelee.my_solo_life.contentsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shinyelee.my_solo_life.databinding.ActivityContentsShowBinding

class ContentsShowActivity : AppCompatActivity() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityContentsShowBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityContentsShowBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 해당 아이템(컨텐츠) 본문의 url을 얻어와서
        val getUrl = intent.getStringExtra("url")

        // 웹뷰에 넣음
        binding.webView.loadUrl(getUrl.toString())

    }

}