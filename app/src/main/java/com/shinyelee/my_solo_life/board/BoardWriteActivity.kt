package com.shinyelee.my_solo_life.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.shinyelee.my_solo_life.databinding.ActivityBoardWriteBinding
import com.shinyelee.my_solo_life.utils.FBAuth
import com.shinyelee.my_solo_life.utils.FBRef

class BoardWriteActivity : AppCompatActivity() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityBoardWriteBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityBoardWriteBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 글쓰기 버튼을 클릭하면 작성한 글이 등록됨
        binding.writeBtn.setOnClickListener {

            // 제목, 본문, uid, 시간
            val title = binding.titleArea.text.toString()
            val main = binding.mainArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()

            // 게시글 정보(제목, 본문, uid, 시간) 넣음
            FBRef.boardRef
                .push()
                .setValue(BoardModel(title, main, uid, time))

            // 등록 확인 메시지 띄움
            Toast.makeText(this, "게시글이 등록되었습니다", Toast.LENGTH_SHORT).show()

            // 글쓰기 액티비티 종료
            finish()

        }

        // 카메라 아이콘
        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        }

    }

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