package com.shinyelee.my_solo_life.contentsList

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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

        // 컨텐츠모델 형식의 데이터 리스트
        val items = ArrayList<ContentsModel>()

        // 리사이클러뷰 어댑터 연결
        val rvAdapter = ContentsRVAdapter(baseContext, items)

        // 파이어베이스
        val database = Firebase.database

        // 카테고리
        val category = intent.getStringExtra("category")

        if(category == "kotlin_syntax") {

            // kotlin_syntax 하위에 데이터 넣음
            val myRef = database.getReference("kotlin_syntax")


            // 데이터베이스에서 게시물의 세부정보를 검색
            val postListener = object : ValueEventListener {

                // 데이터 스냅샷
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // for문으로 출력
                    for(dataModel in dataSnapshot.children) {

                        // 데이터모델 형태로 받아옴
                        val item = dataModel.getValue(ContentsModel::class.java)

                        // 데이터 리스트에 아이템 넣어줌
                        items.add(item!!)

                    }

                    // 아이템의 변경사항을 어댑터에 알려줌
                    rvAdapter.notifyDataSetChanged()

                    // items에 들어간 데이터 확인
                    Log.d("ContentsListActivity", items.toString())

                }

                // 오류 나면
                override fun onCancelled(databaseError: DatabaseError) {

                    // 로그
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

                }

            }

            // 파이어베이스 내 데이터 변화(추가)를 알려줌
            myRef.addValueEventListener(postListener)


        } else if(category == "error_warning") {

            // error_warning 하위에 데이터 넣음
            val myRef = database.getReference("error_warning")


            // 데이터베이스에서 게시물의 세부정보를 검색
            val postListener = object : ValueEventListener {

                // 데이터 스냅샷
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // for문으로 출력
                    for(dataModel in dataSnapshot.children) {

                        // 데이터모델 형태로 받아옴
                        val item = dataModel.getValue(ContentsModel::class.java)

                        // 데이터 리스트에 아이템 넣어줌
                        items.add(item!!)

                    }

                    // 아이템의 변경사항을 어댑터에 알려줌
                    rvAdapter.notifyDataSetChanged()

                    // items에 들어간 데이터 확인
                    Log.d("ContentsListActivity", items.toString())

                }

                // 오류 나면
                override fun onCancelled(databaseError: DatabaseError) {

                    // 로그
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

                }

            }

            // 파이어베이스 내 데이터 변화(추가)를 알려줌
            myRef.addValueEventListener(postListener)


        } else {


            // contents 하위에 데이터 넣음
            val myRef = database.getReference("contents")

            // 데이터베이스에서 게시물의 세부정보를 검색
            val postListener = object : ValueEventListener {

                // 데이터 스냅샷
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // for문으로 출력
                    for(dataModel in dataSnapshot.children) {

                        // 데이터모델 형태로 받아옴
                        val item = dataModel.getValue(ContentsModel::class.java)

                        // 데이터 리스트에 아이템 넣어줌
                        items.add(item!!)

                    }

                    // 아이템의 변경사항을 어댑터에 알려줌
                    rvAdapter.notifyDataSetChanged()

                    // items에 들어간 데이터 확인
                    Log.d("ContentsListActivity", items.toString())

                }

                // 오류 나면
                override fun onCancelled(databaseError: DatabaseError) {

                    // 로그
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

                }

            }

            // 파이어베이스 내 데이터 변화(추가)를 알려줌
            myRef.addValueEventListener(postListener)


        }

        // 리사이클러뷰 어댑터 연결
        val rv : RecyclerView = binding.rv

        rv.adapter = rvAdapter

        // 그리드 레이아웃 매니저 -> 아이템을 격자 형태로 배치(2열)
        rv.layoutManager = GridLayoutManager(this, 2)

        // itemClickListener 작동 테스트
        rvAdapter.itemClick = object: ContentsRVAdapter.ItemClickListener {

            // 클릭하면
            override fun onClick(view: View, position: Int) {

                // 명시적 인텐트
                // -> 다른 액티비티 호출
                val intent = Intent(this@ContentsListActivity, ContentsShowActivity::class.java)

                // 웹뷰로 URL 데이터 넘김
                intent.putExtra("url", items[position].webUrl)

                // 컨텐츠쇼 액티비티 시작
                startActivity(intent)

            }

        }

        // 뒤로가기 버튼 클릭하면
        binding.backBtn.setOnClickListener {

            // 콘텐츠리스트 액티비티 종료
            finish()

        }

//        val myRef4 = database.getReference("VCS")

        // 제목, 이미지, 본문 순으로 들어감(파이어베이스)
//        myRef4.push().setValue(
//            ContentsModel(
//                "",
//                "",
//                "")
//        )

    }

}