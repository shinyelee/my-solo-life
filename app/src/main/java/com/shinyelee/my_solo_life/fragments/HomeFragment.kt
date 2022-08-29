package com.shinyelee.my_solo_life.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.board.BoardLVAdapter
import com.shinyelee.my_solo_life.board.BoardModel
import com.shinyelee.my_solo_life.board.BoardReadActivity
import com.shinyelee.my_solo_life.contentsList.BookmarkRVAdapter
import com.shinyelee.my_solo_life.contentsList.ContentsListActivity
import com.shinyelee.my_solo_life.contentsList.ContentsModel
import com.shinyelee.my_solo_life.databinding.FragmentHomeBinding
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef

class HomeFragment : Fragment() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : FragmentHomeBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    // 게시글(=제목+본문+uid+시간) 목록
    private val boardList = mutableListOf<BoardModel>()
    // 게시글의 키 목록
    private val boardKeyList = mutableListOf<String>()
    // 리스트뷰 어댑터 선언
    private lateinit var boardLVAdapter : BoardLVAdapter

    // 아이템(=컨텐츠=제목+썸네일+본문) 목록
    val items = ArrayList<ContentsModel>()
    // 아이템 키(=아이디) 목록
    val keyList = ArrayList<String>()
    // 북마크 아이디(=키) 목록
    val bookmarkIdList = mutableListOf<String>()
    // 리사이클러뷰 어댑터 선언
    lateinit var rvAdapter: BookmarkRVAdapter

    // 태그
    private val TAG = HomeFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        // 뷰바인딩
        vBinding = FragmentHomeBinding.inflate(inflater, container, false)

        // 블로그 더보기 클릭 -> 블로그 프래그먼트로 이동
        binding.blogMoreText.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_blogFragment)
        }

        // 게시판 더보기 클릭 -> 게시판 프래그먼트로 이동
        binding.boardMoreText.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_boardFragment)
        }

        // 북마크 더보기 클릭 -> 북마크 프래그먼트로 이동
        binding.bookmarkMoreText.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_bookmarkFragment)
        }

        // 안드로이드 아이콘 클릭하면
        binding.androidIcon.setOnClickListener {
            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(context, ContentsListActivity::class.java)
            // android_studio 카테고리로 데이터 넘겨줌
            intent.putExtra("category", "android_studio")
            // 컨텐츠리스트 액티비티 시작
            startActivity(intent)
        }

        // 코틀린 아이콘 -> kotlin_syntax
        binding.kotlinIcon.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category", "kotlin_syntax")
            startActivity(intent)
        }

        // 에러 아이콘 -> error_warning
        binding.errorIcon.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category", "error_warning")
            startActivity(intent)
        }

        // 버전컨트롤시스템 아이콘 -> vcs_github
        binding.vcsIcon.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category", "vcs_github")
            startActivity(intent)
        }

        // 웹 아이콘 -> category
        binding.webIcon.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category", "web_internet")
            startActivity(intent)
        }

        // 기타 아이콘 -> etc (추후 수정)
//        binding.etcIcon.setOnClickListener {}

        // 리스트뷰 어댑터 연결(게시판)
        boardLVAdapter = BoardLVAdapter(boardList)
        val lv : ListView = binding.mainBoardLV
        lv.adapter = boardLVAdapter

        // 게시판 출력
        getBoardListData()

        // 파이어베이스의 게시글 키를 기반으로 게시글 데이터(=제목+본문+uid+시간) 받아옴
        binding.mainBoardLV.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, BoardReadActivity::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }

        // 리사이클러뷰 어댑터 연결(북마크)
        rvAdapter = BookmarkRVAdapter(requireContext(), items, keyList, bookmarkIdList)
        val rv : RecyclerView = binding.mainBookmarkRV
        rv.adapter = rvAdapter

        // 그리드 레이아웃 매니저 -> 아이템을 격자 형태로 배치(2열)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        // 현재 사용자의 북마크(키) 출력
        getBookmarkData()

        // 블로그 버튼
        binding.blogBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_blogFragment)
        }

        // 게시판 버튼
        binding.boardBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_boardFragment)
        }

        // 북마크 버튼
        binding.bookmarkBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_bookmarkFragment)
        }

        // 웹 버튼
        binding.webBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_webFragment)
        }

        // 뷰바인딩
        return binding.root

    }

    // 모든 게시글 정보를 가져옴
    private fun getBoardListData() {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 게시글 목록 비움
                // -> 저장/삭제 마다 데이터 누적돼 게시글 중복으로 저장되는 것 방지
                boardList.clear()

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for(dataModel in dataSnapshot.children) {

                    // 로그
                    Log.d(TAG, dataModel.toString())

                    // 아이템(=게시글)
                    val item = dataModel.getValue(BoardModel::class.java)

                    // 게시글 목록에 아이템 넣음
                    boardList.add(item!!)

                    // 게시글 키 목록에 문자열 형식으로 변환한 키 넣음
                    boardKeyList.add(dataModel.key.toString())

                }
                // getPostData()와 달리 반복문임 -> 아이템'들'

                // 게시글 키 목록을 역순으로 출력
                boardKeyList.reverse()

                // 게시글 목록도 역순 출력
                boardList.reverse()

                // 동기화(새로고침) -> 리스트 크기 및 아이템 변화를 어댑터에 알림
                boardLVAdapter.notifyDataSetChanged()

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

    // 블로그 탭의 모든 컨텐츠 가져옴
    private fun getBlogData() {

        // 데이터베이스에서 게시물의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for(dataModel in dataSnapshot.children) {

                    // 모든 컨텐츠(키, 본문 url, 썸네일 url, 제목) 출력
                    Log.d(TAG, dataModel.toString())

                    // 아이템을 받아
                    val item = dataModel.getValue(ContentsModel::class.java)

                    // 북마크 아이디 목록에 키가 포함(북마크 저장)된 경우만
                    if(bookmarkIdList.contains(dataModel.key.toString())) {

                        // 아이템을 아이템 목록에 넣음
                        items.add(item!!)

                        // 키 값은 아이템 키 목록에 넣음
                        keyList.add(dataModel.key.toString())

                    }

                }

                // 동기화(새로고침) -> 리스트 크기 및 아이템 변화를 어댑터에 알림
                rvAdapter.notifyDataSetChanged()

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 카테고리별 컨텐츠 데이터의 변화(추가)를 알려줌
        FBRef.androidStudio.addValueEventListener(postListener)
        FBRef.kotlinSyntax.addValueEventListener(postListener)
        FBRef.errorWarning.addValueEventListener(postListener)
        FBRef.vcsGithub.addValueEventListener(postListener)
        FBRef.webInternet.addValueEventListener(postListener)

    }

    // 북마크 정보를 가져옴
    private fun getBookmarkData() {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 아이템 목록, 키 목록, 북마크 아이디 목록 비움
                // -> 저장/삭제 마다 데이터 누적돼 북마크 중복으로 저장되는 것 방지
                items.clear()
                keyList.clear()
                bookmarkIdList.clear()

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for(dataModel in dataSnapshot.children) {

                    // 현재 사용자의 북마크(키) 출력
                    Log.e(TAG, dataModel.toString())

                    // 북마크 아이디 목록에 아이템의 키 값을 넣음
                    bookmarkIdList.add(dataModel.key.toString())

                }

                // 모든 컨텐츠 가져옴
                getBlogData()

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터 변화(추가)를 알려줌
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)

    }

}