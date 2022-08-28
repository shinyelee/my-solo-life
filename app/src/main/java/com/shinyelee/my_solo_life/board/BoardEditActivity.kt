package com.shinyelee.my_solo_life.board

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.shinyelee.my_solo_life.databinding.ActivityBoardEditBinding
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef

class BoardEditActivity : AppCompatActivity() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityBoardEditBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    // 게시글 키
    private lateinit var key: String

    // 태그
    private val TAG = BoardEditActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityBoardEditBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 뒤로가기 버튼을 클릭하면
        binding.backBtn.setOnClickListener {

            // 글수정 액티비티 종료
            finish()

        }

        // 글읽기 프래그먼트에서 게시글의 키 값을 받아옴
        key = intent.getStringExtra("key").toString()

        // 키 값을 바탕으로 게시글 하나의 정보를 가져옴
        getBoardData(key)

        // 키 값을 바탕으로 게시글에 첨부된 이미지 정보를 가져옴
        getImageData(key)

        // 수정하기 버튼을 클릭하면
        binding.editBtn.setOnClickListener {

            // 키 값을 바탕으로 불러온 게시글을 수정
            editBoardData(key)

        }

    }

    // 게시글을 수정
    private fun editBoardData(key: String) {

        // 수정한 값으로 업데이트
        FBRef.boardRef.child(key).setValue(BoardModel(

            // 제목 및 본문은 직접 수정한 내용으로,
            binding.titleArea.text.toString(),
            binding.mainArea.text.toString(),

            // uid와 시간은 자동 설정됨
            FBAuth.getUid(),
            FBAuth.getTime())

        )

        // 수정 확인 메시지
        Toast.makeText(this, "게시글이 수정되었습니다", Toast.LENGTH_SHORT).show()

        // 글수정 액티비티 종료
        finish()

   }

    // 게시글에 첨부된 이미지 정보를 가져옴
    private fun getImageData(key: String) {

        // 이미지 파일 경로
        val storageReference = Firebase.storage.reference.child("$key.png")

        // 이미지 넣을 곳
        val imgDown = binding.imageArea

        // 글라이드로 이미지 다운로드
        storageReference.downloadUrl.addOnCompleteListener( { task ->

            // 이미지 첨부
            if(task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .into(imgDown)
            // 첨부 이미지 없으면 imageArea 안 보이게 처리
            } else {
                binding.imageArea.isVisible = false
            }

        })

    }

    // 게시글 하나의 정보를 가져옴
    private fun getBoardData(key: String) {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 예외 처리
                try {

                    // 데이터 스냅샷 내 데이터모델 형식으로 저장된 아이템(=게시글)
                    val item = dataSnapshot.getValue(BoardModel::class.java)

                    // 제목, 본문 해당 영역에 넣음(작성자 및 시간은 직접 수정하지 않음)
                    binding.titleArea.setText(item?.title)
                    binding.mainArea.setText(item?.main)
                    // textView -> .text
                    // editText -> .setText(집어넣을 데이터)

                // 오류 나면
                } catch (e: Exception) {

                    // 로그
                    Log.e(TAG, "getBoardData 확인")

                }

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