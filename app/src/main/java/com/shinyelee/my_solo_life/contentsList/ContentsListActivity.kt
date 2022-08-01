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

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityContentsListBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    lateinit var myRef : DatabaseReference

    val bookmarkIdList = mutableListOf<String>()

    lateinit var rvAdapter : ContentsRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityContentsListBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 데이터(콘텐츠 모델) 넘겨줌
        val items = ArrayList<ContentsModel>()
        items.add(ContentsModel("dataBinding -> viewBinding error (2)", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcjbcPT%2FbtrHN4eVbuv%2FsOtXJrQU89FbDsbxG67DNK%2Fimg.png"))
        items.add(ContentsModel("dataBinding -> viewBinding error (1)", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbSdctw%2FbtrHI2PS3ZL%2FdPipHXtospsMS6FASG8FG1%2Fimg.png"))
        items.add(ContentsModel("push message error (2)", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcrqopJ%2FbtrFpbueRw7%2F4DFoMUj5QjYOhO8ReixI6K%2Fimg.png"))
        items.add(ContentsModel("push message error (1)", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FVEkQJ%2FbtrFaNGvPZG%2FHfnKE2LwuKpklaV1kZOcDk%2Fimg.png"))

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

        // 파이어베이스에서 컨텐츠 받아오는 부분
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

        // 리사이클러뷰 어댑터 연결
        binding.rv.adapter = rvAdapter

        // 그리드레이아웃매니저 -> 아이템을 그리드 형태로 배치(2열로)
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