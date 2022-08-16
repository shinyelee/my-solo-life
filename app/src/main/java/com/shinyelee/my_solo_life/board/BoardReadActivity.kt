package com.shinyelee.my_solo_life.board

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.shinyelee.my_solo_life.databinding.ActivityBoardReadBinding
import com.shinyelee.my_solo_life.utils.FBRef

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

        // 글읽기 프래그먼트에서 게시글의 키 값을 받아옴
        val key = intent.getStringExtra("key")

        // 키 값을 바탕으로 게시글 하나의 정보를 가져옴
        getPostData(key.toString())

    }

    // 게시글 하나의 정보를 가져옴
    private fun getPostData(key: String) {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된 아이템(=게시글)
                val item = dataSnapshot.getValue(BoardModel::class.java)

                // 제목, 시간, 본문 해당 영역에 넣음
                binding.titleArea.text = item!!.title
                binding.timeArea.text = item.time
                binding.mainArea.text = item.main

            }
            // getBoardData()와 달리 반복문이 아님 -> '단일' 아이템

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터의 변화(추가)를 알려줌
        FBRef.boardRef.child(key).addValueEventListener(postListener)

    }

    // 액티비티 파괴시
    override fun onDestroy() {

        // 바인딩 클래스 인스턴스 참조를 정리 -> 메모리 효율이 좋아짐
        vBinding = null
        super.onDestroy()

    }

}