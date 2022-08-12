package com.shinyelee.my_solo_life.contentsList

import android.annotation.SuppressLint
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
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef

class ContentsListActivity : AppCompatActivity() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityContentsListBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    // 데이터베이스에서 데이터를 읽고 쓰려면 DatabaseReference 인스턴스가 필요
    lateinit var myRef : DatabaseReference

    // 북마크의 아이디(=키) 목록
    val bookmarkIdList = mutableListOf<String>()

    // 리사이클러뷰 어댑터 선언
    lateinit var rvAdapter: ContentsRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityContentsListBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 컨텐츠모델 형식의 아이템(=컨텐츠=제목+썸네일+본문) 목록
        val items = ArrayList<ContentsModel>()

        // 각 아이템의 키(=아이디) 목록 -> 북마크에 필요
        val itemKeyList = ArrayList<String>()

        // 리사이클러뷰 어댑터 연결(컨텍스트, 아이템 목록, 아이템 키 목록, 북마크 아이디 목록)
        rvAdapter = ContentsRVAdapter(baseContext, items, itemKeyList, bookmarkIdList)

        // 파이어베이스
        val database = Firebase.database

        // 카테고리
        val category = intent.getStringExtra("category")

        // .getReference() -> 데이터베이스의 루트 폴더 주소 값을 반환
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

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for(dataModel in dataSnapshot.children) {

                    // 아이템을 받아
                    val item = dataModel.getValue(ContentsModel::class.java)

                    // 아이템 목록에 넣음
                    items.add(item!!)

                    // 키 값은 아이템 키 목록에 넣음
                    itemKeyList.add(dataModel.key.toString())

                }

                // 동기화(새로고침) -> 리스트 크기 및 아이템 변화를 어댑터에 알림
                rvAdapter.notifyDataSetChanged()

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터의 변화(추가)를 알려줌
        myRef.addValueEventListener(postListener)

        // 리사이클러뷰 어댑터 연결
        val rv : RecyclerView = binding.rv
        rv.adapter = rvAdapter

        // 그리드 레이아웃 매니저 -> 아이템을 격자 형태로 배치(2열)
        rv.layoutManager = GridLayoutManager(this, 2)

        // 북마크 정보를 가져옴
        getBookmarkData()

        // 파이어베이스에 (웹뷰에서 보여줄) 아이템 데이터 넣기
//        val myRef1 = database.getReference("파이어베이스 카테고리 이름")

        // 제목, 썸네일, 본문 순으로 들어감(파이어베이스)
//        myRef1.push().setValue(
//            ContentsModel(
//                "컨텐츠 제목",
//                "썸네일 URL",
//                "본문 URL")
//        )

        // 뒤로가기 버튼 -> 컨텐츠리스트 액티비티 종료
        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    // 북마크 정보를 가져옴
    private fun getBookmarkData() {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // .clear() -> 북마크 아이디 목록 비움(저장/삭제 마다 데이터 누적되는 것 방지)
                bookmarkIdList.clear()

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for(dataModel in dataSnapshot.children) {

                    // 북마크 아이디 목록에 아이템의 키 값을 넣음
                    bookmarkIdList.add(dataModel.key.toString())

                }

                // 동기화(새로고침) -> 리스트 크기 및 아이템 변화를 어댑터에 알림
                rvAdapter.notifyDataSetChanged()

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터 변화(추가)를 알려줌
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)

    }

}