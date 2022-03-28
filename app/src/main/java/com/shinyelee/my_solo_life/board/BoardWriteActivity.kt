package com.shinyelee.my_solo_life.board

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.contentsList.BookmarkModel
import com.shinyelee.my_solo_life.databinding.ActivityBoardWriteBinding
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardWriteBinding

    private val TAG = BoardWriteActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        binding.writeBtn.setOnClickListener {

            val title = binding.titleArea.text.toString()
            val contents = binding.contentsArea.text.toString()

            Log.d(TAG, title)
            Log.d(TAG, contents)

            FBRef.boardRef
                .push()
                .setValue(BoardModel(title,contents,"uid","time"))

        }
    }
}