package com.shinyelee.my_solo_life.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.ActivityBoardInsideBinding

class BoardInsideActivity : AppCompatActivity() {

    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var binding : ActivityBoardInsideBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        // 첫 번째 방법
//        val title = intent.getStringExtra("title").toString()
//        val contents = intent.getStringExtra("contents").toString()
//        val time = intent.getStringExtra("time").toString()
//
//        binding.titleArea.text = title
//        binding.textArea.text = contents
//        binding.timeArea.text = time

//        Log.d(TAG, title)
//        Log.d(TAG, contents)
//        Log.d(TAG, time)

        // 두 번째 방법
        val key = intent.getStringExtra("key")
        Toast.makeText(this, key, Toast.LENGTH_LONG).show()

    }

}