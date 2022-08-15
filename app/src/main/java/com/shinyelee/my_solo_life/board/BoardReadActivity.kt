package com.shinyelee.my_solo_life.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shinyelee.my_solo_life.databinding.ActivityBoardReadBinding

class BoardReadActivity : AppCompatActivity() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityBoardReadBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    private val TAG = BoardReadActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityBoardReadBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 보드쇼 프래그먼트에서 게시글 데이터(제목, 본문, 시간) 받아와서
        val title = intent.getStringExtra("title").toString()
        val time = intent.getStringExtra("time").toString()
        val main = intent.getStringExtra("main").toString()

        // 해당 게시글 클릭되면 로그로 출력
//        Log.d(TAG, title)
//        Log.d(TAG, time)
//        Log.d(TAG, main)

        // 각 영역에 넣음
        binding.titleArea.text = title
        binding.timeArea.text = time
        binding.mainArea.text = main

    }

    // 액티비티 파괴시
    override fun onDestroy() {

        // 바인딩 클래스 인스턴스 참조를 정리 -> 메모리 효율이 좋아짐
        vBinding = null
        super.onDestroy()

    }

}