package com.shinyelee.my_solo_life.contentsList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        // 뒤로가기 버튼 클릭하면
        binding.backBtn.setOnClickListener {

            // 콘텐츠리스트 액티비티 종료
            finish()

        }

        // 리사이클러뷰 어댑터 연결
        val rv : RecyclerView = binding.rv

        // 리사이클러뷰에 테스트 데이터 넣음
        val items = ArrayList<ContentsModel>()

        // 제목, 이미지, 본문 순으로 들어감
        items.add(ContentsModel(
            "android.databinding.tool.processing.ScopedException: [databinding]",
            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcjbcPT%2FbtrHN4eVbuv%2FsOtXJrQU89FbDsbxG67DNK%2Fimg.png",
            "https://shinye0213.tistory.com/476?category=1047695"))
        items.add(ContentsModel(
            "Cannot access '어쩌고' which is a supertype of '패키지명.databinding.액티비티명Binding'.",
            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbSdctw%2FbtrHI2PS3ZL%2FdPipHXtospsMS6FASG8FG1%2Fimg.png",
            "https://shinye0213.tistory.com/475?category=1047695"))
        items.add(ContentsModel(
            "E/AndroidRuntime: FATAL EXCEPTION: DefaultDispatcher-worker-1 (2)",
            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcrqopJ%2FbtrFpbueRw7%2F4DFoMUj5QjYOhO8ReixI6K%2Fimg.png",
            "https://shinye0213.tistory.com/473?category=1047695"))
        items.add(ContentsModel(
            "E/AndroidRuntime: FATAL EXCEPTION: DefaultDispatcher-worker-1 (1)",
            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FVEkQJ%2FbtrFaNGvPZG%2FHfnKE2LwuKpklaV1kZOcDk%2Fimg.png",
            "https://shinye0213.tistory.com/472?category=1047695"))
        items.add(ContentsModel(
            "패키지.MainActivity is not an Activity subclass or alias",
            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FVv3Jp%2FbtrC3E7WW01%2FJeS6AntHwhXXt5DtBRpWi0%2Fimg.png",
            "https://shinye0213.tistory.com/470?category=1047695"))
        items.add(ContentsModel(
            "System UI Bar Error",
            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fk9q0o%2FbtrBq7JmNyk%2F0i6Thp20dzueTh2kRCRlKk%2Fimg.png",
            "https://shinye0213.tistory.com/454?category=1047695"))
        items.add(ContentsModel(
            "E/ClipboardService: Denying clipboard access to com.android.",
            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbLwxRZ%2FbtrAM2nFkOb%2FPBBtZGc5TUdeqSlCSyoK70%2Fimg.png",
            "https://shinye0213.tistory.com/441?category=1047695"))
        items.add(ContentsModel(
            "Kotlin: [Internal Error] java.rmi.UnexpectedException: unexpected exception; nested exception is:",
            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdnOUIn%2FbtryPTgoVCB%2FERI1WJaXHK7CEjiItyDxyK%2Fimg.png",
            "https://shinye0213.tistory.com/426?category=1047695"))
        items.add(ContentsModel(
            "Unrecognized Android Studio (or Android Support plugin for IntelliJ IDEA) version '202.7660.26.42.7351085',",
            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbjqRTI%2Fbtrti4sRRpa%2FGv9yCVzLCxjpOFB8Qtg5tK%2Fimg.png",
            "https://shinye0213.tistory.com/401?category=1047695"))
        items.add(ContentsModel(
            "java.lang.RuntimeException: Unable to start activity ComponentInfo{패키지명/패키지명.MainActivity}:",
            "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcOnksa%2Fbtrs660xdJT%2FJ3xIZIhrr5G4LQQzq36I11%2Fimg.png",
            "https://shinye0213.tistory.com/399?category=1047695"))

        // 리사이클러뷰 어댑터 연결
        val rvAdapter = ContentsRVAdapter(baseContext, items)
        rv.adapter = rvAdapter

        // 그리드 레이아웃 매니저 -> 아이템을 격자 형태로 배치(2열)
        rv.layoutManager = GridLayoutManager(this, 2)

        // itemClickListener 작동 테스트
        rvAdapter.itemClick = object: ContentsRVAdapter.ItemClickListener {

            // 클릭하면
            override fun onClick(view: View, position: Int) {

                // 해당하는 게시글의 제목을 토스트 메시지로 띄움
//                Toast.makeText(baseContext, items[position].title, Toast.LENGTH_SHORT).show()

                // 명시적 인텐트
                // -> 다른 액티비티 호출
                val intent = Intent(this@ContentsListActivity, ContentsShowActivity::class.java)

                // 웹뷰로 URL 데이터 넘김
                intent.putExtra("url", items[position].webUrl)

                // 컨텐츠쇼 액티비티 시작
                startActivity(intent)

            }

        }

    }

}