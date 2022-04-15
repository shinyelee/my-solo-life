package com.shinyelee.my_solo_life.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.ActivityBoardInsideBinding
import com.shinyelee.my_solo_life.utils.FBRef

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
        getBoardData(key.toString())

    }

    private fun getBoardData(key : String) {

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataModel = dataSnapshot.getValue(BoardModel::class.java)
                Log.d(TAG, dataModel!!.title)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }

        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)

    }

}