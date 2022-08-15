package com.shinyelee.my_solo_life.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shinyelee.my_solo_life.R

class BoardReadActivity : AppCompatActivity() {

    private val TAG = BoardReadActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_read)

        // 보드쇼 프래그먼트에서 게시글 데이터(제목, 본문, 시간) 받아와서
        val title = intent.getStringExtra("title").toString()
        val main = intent.getStringExtra("main").toString()
        val time = intent.getStringExtra("time").toString()

        // 해당 게시글 클릭되면 로그로 출력
        Log.d(TAG, title)
        Log.d(TAG, main)
        Log.d(TAG, time)

    }

}