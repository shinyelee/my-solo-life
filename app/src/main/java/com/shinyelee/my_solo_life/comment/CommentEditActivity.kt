package com.shinyelee.my_solo_life.comment

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.shinyelee.my_solo_life.databinding.ActivityCommentEditBinding
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef

class CommentEditActivity : AppCompatActivity() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityCommentEditBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    // 댓글 키
    private lateinit var commentKey: String

    // 태그
    private val TAG = CommentEditActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityCommentEditBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 글읽기 액티비티에서 댓글의 키 값을 받아옴
        commentKey = intent.getStringExtra("commentKey").toString()

        // 댓글 키 값을 바탕으로 댓글 하나의 정보를 가져옴
        getCommentData(commentKey)

        // 뒤로가기 버튼을 클릭하면
        binding.backBtn.setOnClickListener {

            // 댓글수정 액티비티 종료
            finish()

        }

        // 글읽기 액티비티에서 댓글의 키 값을 받아옴
        commentKey = intent.getStringExtra("commentKey").toString()
        Toast.makeText(this, commentKey, Toast.LENGTH_SHORT).show()

        // 댓글 키 값을 바탕으로 댓글 하나의 정보를 가져옴
        getCommentData(commentKey)

        // 수정하기 버튼을 클릭하면
        binding.commentEditBtn.setOnClickListener {

            // 키 값을 바탕으로 불러온 게시글을 수정
            editCommentData(commentKey)

        }

    }

    // 댓글을 수정
    private fun editCommentData(commentKey: String) {

        // 수정한 값으로 업데이트
        FBRef.commentRef.push().child(commentKey).setValue(CommentModel(

            // 제목 및 본문은 직접 수정한 내용으로,
            binding.commentMainArea.text.toString(),

            // uid와 시간은 자동 설정됨
            FBAuth.getUid(),
            FBAuth.getTime()

        ))

        // 수정 확인 메시지
        Toast.makeText(this, "댓글이 수정되었습니다", Toast.LENGTH_SHORT).show()

        // 댓글수정 액티비티 종료
        finish()

    }

    // 댓글 하나의 정보를 가져옴
    private fun getCommentData(commentKey: String) {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 예외 처리
                try {

                    // 데이터 스냅샷 내 데이터모델 형식으로 저장된 아이템(=게시글)
                    val item = dataSnapshot.getValue(CommentModel::class.java)

                    // 본문 해당 영역에 넣음(작성자 및 시간은 직접 수정하지 않음)
                    binding.commentMainArea.setText(item?.main)
                    // textView -> .text
                    // editText -> .setText(집어넣을 데이터)

                    // 오류 나면
                } catch (e: Exception) {

                    // 로그
                    Log.e(TAG, "getBoardData 확인")

                }

            }
            // getCommentListData()와 달리 반복문이 아님 -> '단일' 아이템

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터의 변화(추가)를 알려줌
        FBRef.commentRef.push().child(commentKey).addValueEventListener(postListener)

    }

    // 액티비티 파괴시
    override fun onDestroy() {

        // 바인딩 클래스 인스턴스 참조를 정리 -> 메모리 효율이 좋아짐
        vBinding = null
        super.onDestroy()

    }

}