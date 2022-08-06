package com.shinyelee.my_solo_life.contentsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.ActivityContentsListBinding

class ContentsListActivity : AppCompatActivity() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityContentsListBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityContentsListBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 리사이클러 뷰 어댑터 연결
//        val rv : RecyclerView = findViewById(R.id.rv)
        val rv : RecyclerView = binding.rv

        // 리사이클러 뷰에 테스트 데이터 넣음
        val items = ArrayList<ContentsModel>()
        items.add(ContentsModel("imageUrl1", "title1"))
        items.add(ContentsModel("imageUrl2", "title2"))
        items.add(ContentsModel("imageUrl3", "title3"))
        items.add(ContentsModel("imageUrl4", "title4"))

        // 리사이클러 뷰 어댑터 연결
        val rvAdapter = ContentsRVAdapter(items)
        rv.adapter = rvAdapter

        // 그리드 레이아웃 매니저 -> 아이템을 격자 형태로 배치(2열)
        rv.layoutManager = GridLayoutManager(this, 2)

    }

}