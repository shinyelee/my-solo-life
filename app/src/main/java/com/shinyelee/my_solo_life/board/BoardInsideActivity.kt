package com.shinyelee.my_solo_life.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.comment.CommentLVAdapter
import com.shinyelee.my_solo_life.comment.CommentModel
import com.shinyelee.my_solo_life.databinding.ActivityBoardInsideBinding
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef
import java.lang.Exception

class BoardInsideActivity : AppCompatActivity() {

    private val TAG = BoardInsideActivity::class.java.simpleName

    // viewBinding
    private var vBinding : ActivityBoardInsideBinding? = null
    private val binding get() = vBinding!!

    private lateinit var key : String

    private val commentDataList = mutableListOf<CommentModel>()

    private lateinit var commentAdapter : CommentLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // viewBinding
        vBinding = ActivityBoardInsideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }

        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)

        binding.commentBtn.setOnClickListener {
            insertComment(key)
        }

        commentAdapter = CommentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter

        getCommentData(key)

    }

    fun getCommentData(key : String) {

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(dataModel in dataSnapshot.children) {
                    val item = dataModel.getValue(CommentModel::class.java)
                    commentDataList.add(item!!)
                }

                commentAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }

        }
        FBRef.commentRef.child(key).addValueEventListener(postListener)

    }

    fun insertComment(key : String) {

        FBRef.commentRef
            .child(key)
            .push()
            .setValue(
                CommentModel(
                    binding.commentArea.text.toString(),
                    FBAuth.getTime()
                )
            )

        Toast.makeText(this, "????????? ?????????????????????", Toast.LENGTH_SHORT).show()
        binding.commentArea.setText("")

    }

    private fun showDialog() {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)

        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("????????? ??????/??????")

        val alertDialog = mBuilder.show()

        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
            Toast.makeText(this, "?????? ???????????? ???????????????", Toast.LENGTH_LONG).show()
            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)
        }

        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {
            FBRef.boardRef.child(key).removeValue()
            Toast.makeText(this, "?????? ?????????????????????", Toast.LENGTH_LONG).show()
            finish()
        }

    }

    private fun getImageData(key : String) {

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->

            if(task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)
            } else {
                binding.getImageArea.isVisible = false
            }

        })

    }

    private fun getBoardData(key : String) {

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {

                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)

                    binding.titleArea.text = dataModel!!.title
                    binding.textArea.text = dataModel!!.contents
                    binding.timeArea.text = dataModel!!.time

                    val myUid = FBAuth.getUid()
                    val writerUid = dataModel.uid

                    if(myUid.equals(writerUid)) {
                        binding.boardSettingIcon.isVisible = true
                    } else {
                        Log.d(TAG, "?????? ???????????? ????????? ????????????")
                    }

                } catch (e : Exception) {

                    Log.d(TAG, "?????? ?????????????????????")

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }

        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)

    }

}