package com.shinyelee.my_solo_life.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.MainActivity
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    // 파이어베이스 인스턴스 선언
    private lateinit var auth: FirebaseAuth

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityJoinBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 파이어베이스 인스턴스 초기화
        auth = Firebase.auth

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityJoinBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 회원가입 버튼 클릭하면
        binding.joinBtn.setOnClickListener {

            // 이메일, 비밀번호, 비밀번호 확인
            val emailTxt = binding.email.text.toString()
            val pwTxt = binding.pw.text.toString()
            val pw2Txt = binding.pw2.text.toString()

            // 회원가입
            auth.createUserWithEmailAndPassword(emailTxt, pwTxt)
                .addOnCompleteListener(this) { task ->

                    // 성공하면
                    if (task.isSuccessful) {

                        // 메인 액티비티로 이동
                        val intent = Intent(this, MainActivity::class.java)

                        // 기존 스택을 비움 -> 뒤로가기 했을 때 이전 화면으로 되돌아가는 것 방지
                        finishAffinity()

                        // 새로 생성한 태스크에 액티비티 추가 or
                        // 이미 스택에 존재하면 상위 액티비티 삭제 및 해당 액티비티 최상위로 올림
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

                        startActivity(intent)

                    // 조건 만족해도
                    } else {

                        // 가입 불가능한 경우가 있음
                        Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()

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