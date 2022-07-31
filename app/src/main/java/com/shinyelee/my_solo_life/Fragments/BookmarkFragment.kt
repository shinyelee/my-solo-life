package com.shinyelee.my_solo_life.Fragments

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

    private val TAG = BookmarkFragment::class.java.simpleName

    // (전역변수) 바인딩 객체 선언
    private var vBinding : FragmentBookmarkBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    val bookmarkIdList = mutableListOf<String>()
    val items = ArrayList<ContentsModel>()
    val itemKeyList = ArrayList<String>()
    lateinit var rvAdapter: BookmarkRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 뷰바인딩
        vBinding = FragmentBookmarkBinding.inflate(inflater, container, false)

        // 1. 전체 카테고리의 컨텐츠 데이터를 다 가져옴
//        getCateData()

        // 2. 사용자가 북마크한 정보를 다 가져옴
        getBookmarkData()

        // 3. 전체 컨텐츠 중 사용자가 북마크한 정보만 보여줌
        rvAdapter = BookmarkRVAdapter(requireContext(), items, itemKeyList, bookmarkIdList)
        val rv : RecyclerView = binding.bookmarkRV
        rv.adapter = rvAdapter
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        // 홈 탭 클릭하면
        binding.homeT.setOnClickListener {

            // 홈 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_homeFragment)

        }

        // 블로그 탭 클릭하면
        binding.blogT.setOnClickListener {

            // 블로그 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_blogFragment)

        }

        // 토크 탭 클릭하면
        binding.talkT.setOnClickListener {

            // 토크 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_talkFragment)

        }

        // 깃허브 탭 클릭하면
        binding.githubT.setOnClickListener {

            // 깃허브 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_githubFragment)

        }

        return binding.root

    }

    // 1. 전체 카테고리의 컨텐츠 데이터를 다 가져옴
    private fun getCateData() {

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(dataModel in dataSnapshot.children) {
                    Log.d(TAG, dataModel.toString())
                    val item = dataModel.getValue(ContentsModel::class.java)

                    // 3. 전체 컨텐츠 중 사용자가 북마크한 정보만 보여줌
                    if(bookmarkIdList.contains(dataModel.key.toString())) {
                        items.add(item!!)
                        itemKeyList.add(dataModel.key.toString())
                    }
                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }

        }
        FBRef.cate1.addValueEventListener(postListener)
        FBRef.cate2.addValueEventListener(postListener)

    }

    // 2. 사용자가 북마크한 정보를 다 가져옴
    private fun getBookmarkData() {

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(dataModel in dataSnapshot.children) {
                    Log.e(TAG, dataModel.toString())
                    bookmarkIdList.add(dataModel.key.toString())
                }
                getCateData()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }

        }
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)

    }

}