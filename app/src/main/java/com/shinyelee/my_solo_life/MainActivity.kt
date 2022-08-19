package com.shinyelee.my_solo_life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.auth.IntroActivity
import com.shinyelee.my_solo_life.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityMainBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    // 파이어베이스 인스턴스 선언
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityMainBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 파이어베이스 인스턴스 초기화
        auth = Firebase.auth

        // 설정 버튼 클릭하면
        binding.settingBtn.setOnClickListener {

            // -> 대화상자 뜸
            settingDialog()

        }

    }

    // 설정 대화상자
    fun settingDialog() {

        // custom_dialog를 뷰 객체로 반환
        val dialogView = LayoutInflater.from(this).inflate(R.layout.setting_dialog, null)

        // 대화상자 생성
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)

        // 대화상자 띄움
        val alertDialog = builder.show()

        // 내가 쓴 글 버튼
//        alertDialog.findViewById<ConstraintLayout>(R.id.myPost)?.setOnClickListener {
            // 내가 쓴 글 모아보는 페이지 만들 예정
//        }

        // 로그아웃 버튼
        alertDialog.findViewById<ConstraintLayout>(R.id.logout)?.setOnClickListener {

            // 로그아웃 실행
            Firebase.auth.signOut()

            // 로그아웃 확인 메시지지
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(this, IntroActivity::class.java)

            // 인트로 액티비티 시작
            startActivity(intent)

            // 메인 액티비티 종료
            finish()

        }

        // 대화상자 종료 버튼
        alertDialog.findViewById<ImageView>(R.id.close)?.setOnClickListener {
            alertDialog.dismiss()
        }

    }

    // 액티비티 파괴시
    override fun onDestroy() {

        // 바인딩 클래스 인스턴스 참조를 정리 -> 메모리 효율이 좋아짐
        vBinding = null
        super.onDestroy()

    }

}