package com.shinyelee.my_solo_life.contentsList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.ActivityContentsListBinding
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef

class ContentsListActivity : AppCompatActivity() {

    // viewBinding
    private var vBinding : ActivityContentsListBinding? = null
    private val binding get() = vBinding!!

    lateinit var myRef : DatabaseReference

    val bookmarkIdList = mutableListOf<String>()

    lateinit var rvAdapter : ContentsRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 뷰바인딩
        vBinding = ActivityContentsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터(콘텐츠 모델) 넘겨줌
        val items = ArrayList<ContentsModel>()

        val itemKeyList = ArrayList<String>()

        // 받아온 데이터를 어댑터에 연결
        rvAdapter = ContentsRVAdapter(baseContext, items, itemKeyList, bookmarkIdList)

        val database = Firebase.database

        val cate = intent.getStringExtra("cate")

        if(cate == "cate1") {
            myRef = database.getReference("contents")
        } else if(cate == "cate2") {
            myRef = database.getReference("contents2")
        }

        // Firebase에서 컨텐츠 받아오는 부분
        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(dataModel in dataSnapshot.children) {
                    Log.d("ContentsListActivity", dataModel.toString())
                    Log.d("ContentsListActivity", dataModel.key.toString())
                    val item = dataModel.getValue(ContentsModel::class.java)
                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())
                }
                rvAdapter.notifyDataSetChanged()
                Log.d("ContentsListActivity", items.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }

        }
        myRef.addValueEventListener(postListener)

        binding.rv.adapter = rvAdapter

        // 레이아웃매니저 -> 아이템을 그리드 형태로 배치(2열로)
        binding.rv.layoutManager = GridLayoutManager(this, 2)

        getBookmarkData()

    }

    private fun getBookmarkData() {
        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bookmarkIdList.clear()
                for(dataModel in dataSnapshot.children) {
                    bookmarkIdList.add(dataModel.key.toString())
                }
                Log.d("Bookmark : ", bookmarkIdList.toString())
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }

        }
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)
    }

}