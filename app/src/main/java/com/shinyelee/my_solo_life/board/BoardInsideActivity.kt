package com.shinyelee.my_solo_life.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shinyelee.my_solo_life.R

class BoardInsideActivity : AppCompatActivity() {

    private val TAG = BoardInsideActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_inside)

        val title = intent.getStringExtra("title").toString()
        val contents = intent.getStringExtra("contents").toString()
        val time = intent.getStringExtra("time").toString()

        Log.d(TAG, title)
        Log.d(TAG, contents)
        Log.d(TAG, time)

    }

}