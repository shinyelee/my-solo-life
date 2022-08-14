package com.shinyelee.my_solo_life.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.navigation.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.board.BoardLVAdapter
import com.shinyelee.my_solo_life.board.BoardModel
import com.shinyelee.my_solo_life.board.BoardWriteActivity
import com.shinyelee.my_solo_life.databinding.FragmentBoardBinding
import com.shinyelee.my_solo_life.utils.FBRef

class BoardFragment : Fragment() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : FragmentBoardBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    // 게시글(=제목+본문+uid+시간) 목록
    private val boardList = mutableListOf<BoardModel>()

    // 리스트뷰 어댑터 선언
    private lateinit var lvAdapter : BoardLVAdapter

    // 태그
    private val TAG = BoardFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        // 뷰바인딩
        vBinding = FragmentBoardBinding.inflate(inflater, container, false)

        // 게시글(=제목+본문+uid+시간) 목록
//        val boardList = mutableListOf<BoardModel>()

        // 게시글 목록에 테스트 데이터 넣기
//        boardList.add(BoardModel("title2", "main2", "uid2", "00-00-00 02:00"))

        // 리스트뷰 어댑터 연결(게시글 목록)
        lvAdapter = BoardLVAdapter(boardList)

        // 리스트뷰 어댑터 연결
        val lv : ListView = binding.lv
        lv.adapter = lvAdapter

        // 글쓰기 버튼 클릭 -> 작성 액티비티로 이동
        binding.writeBtn.setOnClickListener {

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(context, BoardWriteActivity::class.java)

            // 글쓰기 액티비티 시작
            startActivity(intent)

        }

        // 홈 버튼 클릭 -> 홈 프래그먼트로 이동
        binding.homeBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_homeFragment)
        }

        // 블로그 버튼
        binding.blogBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_blogFragment)
        }

        // 북마크 버튼
        binding.bookmarkBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_bookmarkFragment)
        }

        // 웹 버튼
        binding.webBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_webFragment)
        }

        // 모든 게시글 정보를 가져옴
        getBoardData()

        // 뷰바인딩
        return binding.root

    }

    // 모든게시글 정보를 가져옴
    private fun getBoardData() {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardList.clear()

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for(dataModel in dataSnapshot.children) {

                    // 로그
                    Log.d(TAG, dataModel.toString())

                    // 아이템(=게시글)
                    val item = dataModel.getValue(BoardModel::class.java)

                    // 게시글 목록에 아이템 넣음
                    boardList.add(item!!)

                }

                // 동기화(새로고침) -> 리스트 크기 및 아이템 변화를 어댑터에 알림
                lvAdapter.notifyDataSetChanged()

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터의 변화(추가)를 알려줌
        FBRef.boardRef.addValueEventListener(postListener)

    }

}