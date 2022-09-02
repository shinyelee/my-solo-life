package com.shinyelee.my_solo_life.board

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.shinyelee.my_solo_life.comment.CommentEditActivity
import com.shinyelee.my_solo_life.comment.CommentLVAdapter
import com.shinyelee.my_solo_life.comment.CommentModel
import com.shinyelee.my_solo_life.databinding.ActivityBoardReadBinding
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef

class BoardReadActivity : AppCompatActivity() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityBoardReadBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    // 게시글 키
    private lateinit var key: String

    // 댓글(=본문+uid+시간) 목록
    private val commentList = mutableListOf<CommentModel>()

    // 댓글의 키 목록
    private val commentKeyList = mutableListOf<String>()

    // 리스트뷰 어댑터 선언
    private lateinit var commentLVAdapter : CommentLVAdapter

    // 태그
    private val TAG = BoardReadActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityBoardReadBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 리스트뷰 어댑터 연결(댓글 목록)
        commentLVAdapter = CommentLVAdapter(commentList)
        val cLV : ListView = binding.commentLV
        cLV.adapter = commentLVAdapter

        // 댓글 목록(리스트뷰)
        cLV.setOnTouchListener(object : View.OnTouchListener {

            // 리스트뷰를 터치했을 때
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                // 스크롤뷰(화면 전체)의 터치 이벤트를 막으면 -> 리스트뷰(댓글 영역)의 스크롤뷰가 작동함
                binding.boardReadSV.requestDisallowInterceptTouchEvent(true)
                return false

            }

        })

        // 게시판 프래그먼트에서 게시글의 키 값을 받아옴
        key = intent.getStringExtra("key").toString()

        // 게시글 키 값을 바탕으로 게시글 하나의 정보를 가져옴
        getBoardData(key)
        getImageData(key)
        getCommentListData(key)

        // 뒤로가기 버튼을 클릭하면
        binding.backBtn.setOnClickListener {

            // 글읽기 액티비티 종료
            finish()

        }

        // 게시글 설정 버튼
        binding.boardSettingBtn.setOnClickListener {

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(this, BoardEditActivity::class.java)

            // 키 값을 바탕으로 게시글 받아옴
            intent.putExtra("key", key)

            // 글수정 액티비티 시작
            startActivity(intent)

        }

        // 파이어베이스의 댓글 키를 기반으로 댓글 데이터(=본문+uid+시간) 받아옴
        cLV.setOnItemClickListener { parent, view, position, id ->

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(baseContext, CommentEditActivity::class.java)

            // 댓글수정 액티비티로 댓글의 키 값 전달
            intent.putExtra("key", key)

            // 댓글수정 액티비티로 댓글의 키 값 전달
            intent.putExtra("commentKey", commentKeyList[position])

            // 댓글수정 액티비티 시작
            startActivity(intent)

        }

        // 댓글쓰기 버튼
        binding.commentBtn.setOnClickListener {

            // -> 작성한 댓글을 등록
            setComment(key)

        }

    }

    // 댓글 목록 정보 가져옴
    private fun getCommentListData(key: String) {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 댓글 목록 비움
                // -> 저장/삭제 마다 데이터 누적돼 게시글 중복으로 저장되는 것 방지
                commentList.clear()

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for(dataModel in dataSnapshot.children) {

                    // 로그
                    Log.d(TAG, dataModel.toString())

                    // 아이템(=댓글)
                    val item = dataModel.getValue(CommentModel::class.java)

                    // 댓글 목록에 아이템 넣음
                    commentList.add(item!!)

                    // 댓글 키 목록에 문자열 형식으로 변환한 키 넣음
                    commentKeyList.add(dataModel.key.toString())

                    // 댓글 헤더에 댓글 개수 출력
                    binding.commentCountText.text = commentKeyList.count().toString()

                }
                // 반복문임 -> 아이템'들'

                // 댓글 키 목록을 출력
                commentKeyList

                // 댓글 목록도 출력
                commentList

                // 동기화(새로고침) -> 리스트 크기 및 아이템 변화를 어댑터에 알림
                commentLVAdapter.notifyDataSetChanged()

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터의 변화(추가)를 알려줌
        FBRef.commentRef.child(key).addValueEventListener(postListener)

    }

    // 작성한 댓글을 등록
    fun setComment(key: String) {

        // 댓글의 데이터(본문, uid, 시간)
        val main = binding.commentMainArea.text.toString()
        val uid = FBAuth.getUid()
        val time = FBAuth.getTime()

        // 키 값 하위에 데이터 넣음
        FBRef.commentRef
            .child(key)
            .push()
            .setValue(CommentModel(main, uid, time))

        // 등록 확인 메시지 띄움
        Toast.makeText(this, "댓글이 등록되었습니다", Toast.LENGTH_SHORT).show()

        // 댓글 입력란 비움
        binding.commentMainArea.text = null

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

                    // 제목, 시간, 본문 해당 영역에 넣음
                    binding.titleArea.text = item!!.title
                    binding.timeArea.text = item.time
                    binding.mainArea.text = item.main

                    // 게시글 작성자와 현재 사용자의 uid를 비교해
                    val writerUid = item.uid
                    val myUid = FBAuth.getUid()

                    // 작성자가 사용자면 수정 버튼 보임
                    binding.boardSettingBtn.isVisible = writerUid.equals(myUid)

                // 오류 나면
                } catch (e: Exception) {

                    // 로그
                    Log.d(TAG, "getBoardData 확인")

                }

            }
            // getBoardListData()와 달리 반복문이 아님 -> '단일' 아이템

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