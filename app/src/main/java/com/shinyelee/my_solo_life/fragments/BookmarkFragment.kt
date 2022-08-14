package com.shinyelee.my_solo_life.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.contentsList.BookmarkRVAdapter
import com.shinyelee.my_solo_life.contentsList.ContentsModel
import com.shinyelee.my_solo_life.databinding.FragmentBookmarkBinding
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef

class BookmarkFragment : Fragment() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : FragmentBookmarkBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    // 태그 -> 로그 찍을 때 편함
    private val TAG = BookmarkFragment::class.java.simpleName

    // 아이템(=컨텐츠=제목+썸네일+본문) 목록
    val items = ArrayList<ContentsModel>()

    // 아이템 키(=아이디) 목록
    val keyList = ArrayList<String>()

    // 북마크 아이디(=키) 목록
    val bookmarkIdList = mutableListOf<String>()

    lateinit var rvAdapter: BookmarkRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        // 뷰바인딩
        vBinding = FragmentBookmarkBinding.inflate(inflater, container, false)

        // 1. 모든 컨텐츠 가져옴
//        getBlogData()

        // 2. 현재 사용자의 북마크(키) 출력
        getBookmarkData()

        // 3. 1과 2의 교집합만 출력

        // 리사이클러뷰 어댑터 연결
        rvAdapter = BookmarkRVAdapter(requireContext(), items, keyList, bookmarkIdList)
        val rv : RecyclerView = binding.bookmarkRV
        rv.adapter = rvAdapter

        // 그리드 레이아웃 매니저 -> 아이템을 격자 형태로 배치(2열)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        // 홈 버튼 클릭 -> 홈 프래그먼트로 이동
        binding.homeBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_homeFragment)
        }

        // 블로그 버튼
        binding.blogBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_blogFragment)
        }

        // 게시판 버튼
        binding.boardBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_boardFragment)
        }

        // 웹 버튼
        binding.webBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_webFragment)
        }

        // 뷰바인딩
        return binding.root

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