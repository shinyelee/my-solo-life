package com.shinyelee.my_solo_life.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.shinyelee.my_solo_life.databinding.ActivityBoardWriteBinding
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef
import java.io.ByteArrayOutputStream

class BoardWriteActivity : AppCompatActivity() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding: ActivityBoardWriteBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    // 게시글 키
    private lateinit var key: String

    // 이미지 업로드 여부
    private var isImageUpload = false

    // 태그
    private val TAG = BoardWriteActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityBoardWriteBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 키 값 기반으로 데이터를 받아오기 위해 키 값 선생성
        key = FBRef.boardRef.push().key.toString()

        // 뒤로가기 버튼을 클릭하면
        binding.backBtn.setOnClickListener {

            // 글쓰기 액티비티 종료
            finish()

        }

        // 글쓰기 버튼
        binding.writeBtn.setOnClickListener {

            // -> 작성한 글을 등록
            setBoard(key)

        }

        // 카메라 아이콘
        binding.imageArea.setOnClickListener {

            // -> 갤러리 실행
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)

            // 이미지 업로드 여부
            isImageUpload = true

        }

    }

    // 작성한 글을 등록
    private fun setBoard(key: String) {

        // 게시글의 데이터(제목, 본문, uid, 시간)
        val title = binding.titleArea.text.toString()
        val main = binding.mainArea.text.toString()
        val uid = FBAuth.getUid()
        val time = FBAuth.getTime()

        // 키 값 하위에 데이터 넣음
        FBRef.boardRef
            .child(key)
            .setValue(BoardModel(title, main, uid, time))

        // 카메라 아이콘을 클릭했다면 이미지 업로드
        if (isImageUpload == true) {

            // 이미지 파일명을 아무렇게나 설정하면 해당 게시글과 매칭하기 어려움
            // -> 키 값과 똑같이 설정하면 해결
            imageUpload("$key.png")

        }
        // 카메라 아이콘을 클릭하지 않음 -> 이미지를 업로드하지 않음

        // 등록 확인 메시지 띄움
        Toast.makeText(this, "게시글이 등록되었습니다", Toast.LENGTH_SHORT).show()

        // 글쓰기 액티비티 종료
        finish()

        // 게시글 쓰면 게시판 프래그먼트로 돌아감

        // 내가 원하는 것 //
        // 글쓰기 액티비티 종료 -> 방금 쓴 글(글읽기 액티비티)로 이동

    }

    // 게시글에 이미지 첨부
    private fun imageUpload(key: String) {

        // Cloud Storage에 파일을 업로드하려면
        val storage = Firebase.storage

        // -> 우선 파일 이름을 포함하여 파일의 전체 경로를 가리키는 참조를 만듦
        val storageRef = storage.reference

        // 임의의 게시글 하나와 그 게시글 내 이미지 하나를 쉽게 매칭하려면
        // -> DB 내 게시글 키 값과 첨부한 이미지 이름이 똑같으면 됨
        val testRef = storageRef.child(key)

        // 적절한 참조를 만들었으면
        binding.imageArea.isDrawingCacheEnabled = true
        binding.imageArea.buildDrawingCache()
        val bitmap = (binding.imageArea.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        // -> put어쩌고() 메서드를 호출하여 Cloud Storage에 파일을 업로드
        var uploadTask = testRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.d(TAG, "imageUpload() failed")
        }.addOnSuccessListener { taskSnapshot ->
            // 성공
        }

    }

    // startActivityForResult와 세트
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100) {
            binding.imageArea.setImageURI(data?.data)
        }
    }

    // 액티비티 파괴시
    override fun onDestroy() {

        // 바인딩 클래스 인스턴스 참조를 정리 -> 메모리 효율이 좋아짐
        vBinding = null
        super.onDestroy()

    }

}