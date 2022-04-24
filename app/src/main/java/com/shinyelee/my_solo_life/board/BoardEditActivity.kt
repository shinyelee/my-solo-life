package com.shinyelee.my_solo_life.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.ActivityBoardEditBinding
import com.shinyelee.my_solo_life.utils.FBRef
import java.lang.Exception

class BoardEditActivity : AppCompatActivity() {

    private lateinit var key : String

    private lateinit var binding : ActivityBoardEditBinding

    private val TAG = BoardEditActivity::class.java

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_edit)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        key = intent.getStringExtra("key").toString()

    }

}