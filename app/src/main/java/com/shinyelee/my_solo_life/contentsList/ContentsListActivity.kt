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

        // 파이어베이스
        val database = Firebase.database

        // contents 하위에 데이터 넣음
        val myRef = database.getReference("contents")

        // 데이터베이스에서 게시물의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 데이터 모델 -> for문 출력
                for(dataModel in dataSnapshot.children) {
                    Log.d("ContentsListActivity", dataModel.toString())
                }

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

        // 리사이클러뷰에 테스트 데이터 직접 넣음
        val items = ArrayList<ContentsModel>()

        // 리사이클러뷰 어댑터 연결
        val rvAdapter = ContentsRVAdapter(baseContext, items)
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

    }

}

// 제목, 이미지, 본문 순으로 들어감(수동)
//        items.add(ContentsModel(
//            "android.databinding.tool.processing.ScopedException: [databinding]",
//            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcjbcPT%2FbtrHN4eVbuv%2FsOtXJrQU89FbDsbxG67DNK%2Fimg.png",
//            "https://shinye0213.tistory.com/476?category=1047695"))
//        items.add(ContentsModel(
//            "Cannot access '어쩌고' which is a supertype of '패키지명.databinding.액티비티명Binding'.",
//            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbSdctw%2FbtrHI2PS3ZL%2FdPipHXtospsMS6FASG8FG1%2Fimg.png",
//            "https://shinye0213.tistory.com/475?category=1047695"))
//        items.add(ContentsModel(
//            "E/AndroidRuntime: FATAL EXCEPTION: DefaultDispatcher-worker-1 (2)",
//            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcrqopJ%2FbtrFpbueRw7%2F4DFoMUj5QjYOhO8ReixI6K%2Fimg.png",
//            "https://shinye0213.tistory.com/473?category=1047695"))
//        items.add(ContentsModel(
//            "E/AndroidRuntime: FATAL EXCEPTION: DefaultDispatcher-worker-1 (1)",
//            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FVEkQJ%2FbtrFaNGvPZG%2FHfnKE2LwuKpklaV1kZOcDk%2Fimg.png",
//            "https://shinye0213.tistory.com/472?category=1047695"))
//        items.add(ContentsModel(
//            "패키지.MainActivity is not an Activity subclass or alias",
//            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FVv3Jp%2FbtrC3E7WW01%2FJeS6AntHwhXXt5DtBRpWi0%2Fimg.png",
//            "https://shinye0213.tistory.com/470?category=1047695"))
//        items.add(ContentsModel(
//            "System UI Bar Error",
//            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fk9q0o%2FbtrBq7JmNyk%2F0i6Thp20dzueTh2kRCRlKk%2Fimg.png",
//            "https://shinye0213.tistory.com/454?category=1047695"))
//        items.add(ContentsModel(
//            "E/ClipboardService: Denying clipboard access to com.android.",
//            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbLwxRZ%2FbtrAM2nFkOb%2FPBBtZGc5TUdeqSlCSyoK70%2Fimg.png",
//            "https://shinye0213.tistory.com/441?category=1047695"))
//        items.add(ContentsModel(
//            "Kotlin: [Internal Error] java.rmi.UnexpectedException: unexpected exception; nested exception is:",
//            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdnOUIn%2FbtryPTgoVCB%2FERI1WJaXHK7CEjiItyDxyK%2Fimg.png",
//            "https://shinye0213.tistory.com/426?category=1047695"))
//        items.add(ContentsModel(
//            "Unrecognized Android Studio (or Android Support plugin for IntelliJ IDEA) version '202.7660.26.42.7351085',",
//            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbjqRTI%2Fbtrti4sRRpa%2FGv9yCVzLCxjpOFB8Qtg5tK%2Fimg.png",
//            "https://shinye0213.tistory.com/401?category=1047695"))
//        items.add(ContentsModel(
//            "java.lang.RuntimeException: Unable to start activity ComponentInfo{패키지명/패키지명.MainActivity}:",
//            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcOnksa%2Fbtrs660xdJT%2FJ3xIZIhrr5G4LQQzq36I11%2Fimg.png",
//            "https://shinye0213.tistory.com/399?category=1047695"))

// 제목, 이미지, 본문 순으로 들어감(파이어베이스)
//        myRef.push().setValue(
//            ContentsModel(
//                "android.databinding.tool.processing.ScopedException: [databinding]",
//                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcjbcPT%2FbtrHN4eVbuv%2FsOtXJrQU89FbDsbxG67DNK%2Fimg.png",
//                "https://shinye0213.tistory.com/476?category=1047695")
//        )
//        myRef.push().setValue(
//            ContentsModel(
//                "Cannot access '어쩌고' which is a supertype of '패키지명.databinding.액티비티명Binding'.",
//                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbSdctw%2FbtrHI2PS3ZL%2FdPipHXtospsMS6FASG8FG1%2Fimg.png",
//                "https://shinye0213.tistory.com/475?category=1047695")
//        )
//        myRef.push().setValue(
//            ContentsModel(
//                "E/AndroidRuntime: FATAL EXCEPTION: DefaultDispatcher-worker-1 (2)",
//                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcrqopJ%2FbtrFpbueRw7%2F4DFoMUj5QjYOhO8ReixI6K%2Fimg.png",
//                "https://shinye0213.tistory.com/473?category=1047695")
//        )
//        myRef.push().setValue(
//            ContentsModel(
//                "E/AndroidRuntime: FATAL EXCEPTION: DefaultDispatcher-worker-1 (1)",
//                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FVEkQJ%2FbtrFaNGvPZG%2FHfnKE2LwuKpklaV1kZOcDk%2Fimg.png",
//                "https://shinye0213.tistory.com/472?category=1047695")
//        )
//        myRef.push().setValue(
//            ContentsModel(
//                "패키지.MainActivity is not an Activity subclass or alias",
//                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FVv3Jp%2FbtrC3E7WW01%2FJeS6AntHwhXXt5DtBRpWi0%2Fimg.png",
//                "https://shinye0213.tistory.com/470?category=1047695")
//        )
//        myRef.push().setValue(
//            ContentsModel(
//                "System UI Bar Error",
//                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fk9q0o%2FbtrBq7JmNyk%2F0i6Thp20dzueTh2kRCRlKk%2Fimg.png",
//                "https://shinye0213.tistory.com/454?category=1047695")
//        )
//        myRef.push().setValue(
//            ContentsModel(
//                "E/ClipboardService: Denying clipboard access to com.android.",
//                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbLwxRZ%2FbtrAM2nFkOb%2FPBBtZGc5TUdeqSlCSyoK70%2Fimg.png",
//                "https://shinye0213.tistory.com/441?category=1047695")
//        )
//        myRef.push().setValue(
//            ContentsModel(
//                "Kotlin: [Internal Error] java.rmi.UnexpectedException: unexpected exception; nested exception is:",
//                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdnOUIn%2FbtryPTgoVCB%2FERI1WJaXHK7CEjiItyDxyK%2Fimg.png",
//                "https://shinye0213.tistory.com/426?category=1047695")
//        )
//        myRef.push().setValue(
//            ContentsModel(
//                "Unrecognized Android Studio (or Android Support plugin for IntelliJ IDEA) version '202.7660.26.42.7351085',",
//                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbjqRTI%2Fbtrti4sRRpa%2FGv9yCVzLCxjpOFB8Qtg5tK%2Fimg.png",
//                "https://shinye0213.tistory.com/401?category=1047695")
//        )
//        myRef.push().setValue(
//            ContentsModel(
//                "java.lang.RuntimeException: Unable to start activity ComponentInfo{패키지명/패키지명.MainActivity}:",
//                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcOnksa%2Fbtrs660xdJT%2FJ3xIZIhrr5G4LQQzq36I11%2Fimg.png",
//                "https://shinye0213.tistory.com/399?category=1047695")
//        )