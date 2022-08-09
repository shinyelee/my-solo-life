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

        // 컨텐츠모델 형식의 데이터 리스트
        val items = ArrayList<ContentsModel>()

        // 리사이클러뷰 어댑터 연결
        val rvAdapter = ContentsRVAdapter(baseContext, items)

        // 데이터베이스에서 게시물의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // for문으로 출력
                for(dataModel in dataSnapshot.children) {

//                    Log.d("ContentsListActivity", dataModel.toString())

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

        val myRef2 = database.getReference("kotlin_syntax")

        myRef2.push().setValue(
            ContentsModel(
                "상속",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FnEPuE%2FbtrCQvi3YSo%2FcMRjDiuVCDQPgvroYSrL81%2Fimg.png",
                "https://shinye0213.tistory.com/469")
        )
        myRef2.push().setValue(
            ContentsModel(
                "생성자",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbrirUt%2FbtrCKiD8SrX%2F7qVMEj0nPgEE8jUkGOUIkK%2Fimg.png",
                "https://shinye0213.tistory.com/468")
        )
        myRef2.push().setValue(
            ContentsModel(
                "코루틴",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbubjX5%2Fbtry8iOtxnj%2FISUK7IdAHLfg4RkCAxhnq1%2Fimg.png",
                "https://shinye0213.tistory.com/432")
        )
        myRef2.push().setValue(
            ContentsModel(
                "비트 연산",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbGLiGM%2Fbtry5CxS1JE%2FpzZxHkF36ZWt30mqoupX0k%2Fimg.png",
                "https://shinye0213.tistory.com/431")
        )
        myRef2.push().setValue(
            ContentsModel(
                "상수, 늦은 초기화, 지연 대리자 속성",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbDcw2p%2FbtryV2klldH%2FL5jNcgkfKbLwzvOkEprfF1%2Fimg.png",
                "https://shinye0213.tistory.com/430")
        )
        myRef2.push().setValue(
            ContentsModel(
                "컬렉션 함수 (2) associateBy, groupBy 등",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FoDU7C%2FbtryRm98kag%2FWqra4iCkwrXmToGoyby3tK%2Fimg.png",
                "https://shinye0213.tistory.com/429")
        )
        myRef2.push().setValue(
            ContentsModel(
                "컬렉션 함수 (1) forEach, filter 등",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbmnt69%2FbtryRQqeOZj%2F9441N8jjBrFqY2Plq9PpSK%2Fimg.png",
                "https://shinye0213.tistory.com/428")
        )
        myRef2.push().setValue(
            ContentsModel(
                "컬렉션 (2) set, map",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkFRkY%2FbtryPaQyh5p%2FxUEwz7MRyjG8aM62YLRmFK%2Fimg.png",
                "https://shinye0213.tistory.com/427")
        )
        myRef2.push().setValue(
            ContentsModel(
                "데이터 클래스, 열거형 클래스",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FnKxnY%2FbtryQZ0MSjW%2FzfFkHq3OkQIraaPZXGYrP0%2Fimg.png",
                "https://shinye0213.tistory.com/425")
        )
        myRef2.push().setValue(
            ContentsModel(
                "중첩 클래스, 내부 클래스",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FGMflf%2FbtryQRoaqXw%2FKPAPokkS2iCKv55unYkAT1%2Fimg.png",
                "https://shinye0213.tistory.com/424")
        )
        myRef2.push().setValue(
            ContentsModel(
                "함수의 인자, 중위 함수",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FceP9X7%2FbtryOUlfeV7%2FcJKiLsN3R9Ow5ZGGNB7By1%2Fimg.png",
                "https://shinye0213.tistory.com/423")
        )
        myRef2.push().setValue(
            ContentsModel(
                "변수의 동일성",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fw7aiE%2FbtryHLCYuaa%2FKmrrqxa5nVh7IMUgUqwpg1%2Fimg.png",
                "https://shinye0213.tistory.com/422")
        )
        myRef2.push().setValue(
            ContentsModel(
                "null 처리",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F7tJqy%2FbtryKW36FfI%2FA5otAUIJrETTki8lSCNPr0%2Fimg.png",
                "https://shinye0213.tistory.com/421")
        )
        myRef2.push().setValue(
            ContentsModel(
                "문자열 가공하기",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb7ZbUN%2FbtryJjSwXPS%2FHv7zGAqfBMEy6NxfN9CmI1%2Fimg.png",
                "https://shinye0213.tistory.com/420")
        )
        myRef2.push().setValue(
            ContentsModel(
                "컬렉션 (1) 리스트",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbR4QmZ%2FbtryFnO32tu%2F9ZueDKCQy9ar2YijvJAE41%2Fimg.png",
                "https://shinye0213.tistory.com/419")
        )
        myRef2.push().setValue(
            ContentsModel(
                "제너릭",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FnEPuE%2FbtrCQvi3YSo%2FcMRjDiuVCDQPgvroYSrL81%2Fimg.png",
                "https://shinye0213.tistory.com/418")
        )
        myRef2.push().setValue(
            ContentsModel(
                "클래스의 다형성",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbrirUt%2FbtrCKiD8SrX%2F7qVMEj0nPgEE8jUkGOUIkK%2Fimg.png",
                "https://shinye0213.tistory.com/417")
        )
        myRef2.push().setValue(
            ContentsModel(
                "옵저버 패턴, 익명 객체",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbubjX5%2Fbtry8iOtxnj%2FISUK7IdAHLfg4RkCAxhnq1%2Fimg.png",
                "https://shinye0213.tistory.com/416")
        )
        myRef2.push().setValue(
            ContentsModel(
                "오브젝트",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbGLiGM%2Fbtry5CxS1JE%2FpzZxHkF36ZWt30mqoupX0k%2Fimg.png",
                "https://shinye0213.tistory.com/415")
        )
        myRef2.push().setValue(
            ContentsModel(
                "스코프 함수",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbDcw2p%2FbtryV2klldH%2FL5jNcgkfKbLwzvOkEprfF1%2Fimg.png",
                "https://shinye0213.tistory.com/414")
        )
        myRef2.push().setValue(
            ContentsModel(
                "고차함수, 람다함수, Unit",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FoDU7C%2FbtryRm98kag%2FWqra4iCkwrXmToGoyby3tK%2Fimg.png",
                "https://shinye0213.tistory.com/413")
        )
        myRef2.push().setValue(
            ContentsModel(
                "스코프, 접근제한자",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbmnt69%2FbtryRQqeOZj%2F9441N8jjBrFqY2Plq9PpSK%2Fimg.png",
                "https://shinye0213.tistory.com/412")
        )
        myRef2.push().setValue(
            ContentsModel(
                "오버라이딩, 추상 클래스, 인터페이스",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkFRkY%2FbtryPaQyh5p%2FxUEwz7MRyjG8aM62YLRmFK%2Fimg.png",
                "https://shinye0213.tistory.com/411")
        )
        myRef2.push().setValue(
            ContentsModel(
                "클래스",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FnKxnY%2FbtryQZ0MSjW%2FzfFkHq3OkQIraaPZXGYrP0%2Fimg.png",
                "https://shinye0213.tistory.com/410")
        )
        myRef2.push().setValue(
            ContentsModel(
                "흐름 제어, 논리 연산자",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FGMflf%2FbtryQRoaqXw%2FKPAPokkS2iCKv55unYkAT1%2Fimg.png",
                "https://shinye0213.tistory.com/409")
        )
        myRef2.push().setValue(
            ContentsModel(
                "반복문, 증감 연산자",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FceP9X7%2FbtryOUlfeV7%2FcJKiLsN3R9Ow5ZGGNB7By1%2Fimg.png",
                "https://shinye0213.tistory.com/408")
        )
        myRef2.push().setValue(
            ContentsModel(
                "조건식, 비교 연산자",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fw7aiE%2FbtryHLCYuaa%2FKmrrqxa5nVh7IMUgUqwpg1%2Fimg.png",
                "https://shinye0213.tistory.com/407")
        )
        myRef2.push().setValue(
            ContentsModel(
                "타입 추론, 함수",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F7tJqy%2FbtryKW36FfI%2FA5otAUIJrETTki8lSCNPr0%2Fimg.png",
                "https://shinye0213.tistory.com/406")
        )
        myRef2.push().setValue(
            ContentsModel(
                "형 변환, 배열",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb7ZbUN%2FbtryJjSwXPS%2FHv7zGAqfBMEy6NxfN9CmI1%2Fimg.png",
                "https://shinye0213.tistory.com/405")
        )
        myRef2.push().setValue(
            ContentsModel(
                "변수, 자료형",
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbR4QmZ%2FbtryFnO32tu%2F9ZueDKCQy9ar2YijvJAE41%2Fimg.png",
                "https://shinye0213.tistory.com/404")
        )

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

    }

}