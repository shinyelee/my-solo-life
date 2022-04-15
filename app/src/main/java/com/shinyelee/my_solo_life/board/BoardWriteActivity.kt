package com.shinyelee.my_solo_life.board

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.contentsList.BookmarkModel
import com.shinyelee.my_solo_life.databinding.ActivityBoardWriteBinding
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef
import java.io.ByteArrayOutputStream

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardWriteBinding

    private val TAG = BoardWriteActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        binding.writeBtn.setOnClickListener {

            val title = binding.titleArea.text.toString()
            val contents = binding.contentsArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()

            Log.d(TAG, title)
            Log.d(TAG, contents)

            FBRef.boardRef
                .push()
                .setValue(BoardModel(title, contents, uid, time))

            Toast.makeText(this, "게시글 등록 완료!", Toast.LENGTH_LONG).show()

            imageUpload()

            finish()

        }

        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        }

    }

    private fun imageUpload() {
        // Get the data from an ImageView as bytes

        val storage = Firebase.storage
        val storageRef = storage.reference
        val kittyRef = storageRef.child("kitty.jpg")

        val imageView = binding.imageArea
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()

        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val data = baos.toByteArray()

        var uploadTask = kittyRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100) {
        binding.imageArea.setImageURI(data?.data)
        }
    }
}