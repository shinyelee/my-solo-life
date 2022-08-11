package com.shinyelee.my_solo_life.contentsList

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.databinding.ActivityContentsListBinding
import com.shinyelee.my_solo_life.utils.FBRef

class ContentsListActivity : AppCompatActivity() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityContentsListBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    // lateinit var
    // -> 우선 데이터 타입(파이어베이스 DB)만 정해놓고 값은 나중에 넣음
    lateinit var myRef : DatabaseReference

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

        // 컨텐츠(블로그 게시글)의 ID값
        // -> 북마크에 필요
        val itemKeyList = ArrayList<String>()

        // 리사이클러뷰 어댑터 연결
        val rvAdapter = ContentsRVAdapter(baseContext, items, itemKeyList)

        // 파이어베이스
        val database = Firebase.database

        // 카테고리
        val category = intent.getStringExtra("category")

        // 카테고리에 해당하는 데이터를 파이어베이스에서 가져옴
        when (category) {
            "android_studio" -> {
                myRef = database.getReference("android_studio")
            }
            "kotlin_syntax" -> {
                myRef = database.getReference("kotlin_syntax")
            }
            "error_warning" -> {
                myRef = database.getReference("error_warning")
            }
            "vcs_github" -> {
                myRef = database.getReference("vcs_github")
            }
            "web_internet" -> {
                myRef = database.getReference("web_internet")
            }
        }

        // 데이터베이스에서 게시물의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // for문으로 출력
                for(dataModel in dataSnapshot.children) {

                    Log.d("ContentsListActivity", dataModel.key.toString())
                    Log.d("ContentsListActivity", dataModel.toString())

                    // 데이터모델 형태로 받아옴
                    val item = dataModel.getValue(ContentsModel::class.java)

                    // 데이터 리스트에 아이템 넣어줌
                    items.add(item!!)

                    // 아이템 키 값도 넣어줌
                    // -> 북마크에 필요
                    itemKeyList.add(dataModel.key.toString())

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

        // 리사이클러뷰 어댑터 연결
        val rv : RecyclerView = binding.rv
        rv.adapter = rvAdapter

        // 그리드 레이아웃 매니저 -> 아이템을 격자 형태로 배치(2열)
        rv.layoutManager = GridLayoutManager(this, 2)

        getBookmarkData()

        // 파이어베이스에 웹뷰 데이터 넣기
//        val myRef1 = database.getReference("파이어베이스 카테고리 이름")

        // 제목, 이미지, 본문 순으로 들어감(파이어베이스)
//        myRef1.push().setValue(
//            ContentsModel(
//                "게시글 제목",
//                "이미지 URL",
//                "게시글 URL")
//        )

        // 뒤로가기 버튼 클릭하면
        binding.backBtn.setOnClickListener {

            // 콘텐츠리스트 액티비티 종료
            finish()

        }

    }

    // 북마크 정보를 가져옴
    private fun getBookmarkData() {

        // 데이터베이스에서 게시물의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // for문으로 출력
                for(dataModel in dataSnapshot.children) {

                    // 북마크 데이터(게시글 키 값, 현재 사용자 UID 값) 로그 찍어봄
                    Log.d("getBookmarkData", dataModel.key.toString())
                    Log.d("getBookmarkData", dataModel.toString())

                }

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터 변화(추가)를 알려줌
        FBRef.bookmarkRef.addValueEventListener(postListener)

    }

}