package com.shinyelee.my_solo_life.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.MainActivity
import com.shinyelee.my_solo_life.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private val TAG = "IntroActivity"

    // 파이어베이스 인스턴스 선언
    private lateinit var auth: FirebaseAuth

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityIntroBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 파이어베이스 인스턴스 초기화
        auth = Firebase.auth

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityIntroBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 로그인 버튼 클릭하면
        binding.loginBtn.setOnClickListener {

            // 로그인 액티비티로 이동
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        // 회원가입 버튼 클릭하면
        binding.joinBtn.setOnClickListener {

            // 조인 액티비티로 이동
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)

        }

        // 비회원 버튼 클릭하면
        binding.guestBtn.setOnClickListener {

            // 익명으로 로그인
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->

                    // 성공하면
                    if (task.isSuccessful) {

                        // 메인 액티비티로 이동
                        val intent = Intent(this, MainActivity::class.java)

                        // 기존 스택을 비움 -> 뒤로가기 했을 때 로그인 화면으로 되돌아가는 것 방지
                        finishAffinity()
                        startActivity(intent)

                    // 실패하면
                    } else {

                        // 토스트 메시지 띄움
                        Toast.makeText(this, "비회원 로그인에 실패했습니다", Toast.LENGTH_LONG).show()

                    }

                }

        }

    }

    // 액티비티 파괴시
    override fun onDestroy() {

        // 바인딩 클래스 인스턴스 참조를 정리
        // -> 메모리 효율이 좋아짐
        vBinding = null
        super.onDestroy()

    }

}
