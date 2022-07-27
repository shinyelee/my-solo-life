package com.shinyelee.my_solo_life.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.MainActivity
import com.shinyelee.my_solo_life.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    private val TAG = "JoinActivity"

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

            // 회원가입 조건 확인
            var emailCheck = true
            var pwCheck = true
            var pw2Check = true
            var pwDoubleCheck = true
            var allCheck = emailCheck and pwCheck and pw2Check and pwDoubleCheck

            // 이메일, 비밀번호, 비밀번호 확인
            val emailTxt = binding.email.text.toString()
            val pwTxt = binding.pw.text.toString()
            val pw2Txt = binding.pw2.text.toString()

            // 이메일 정규식
            val emailPattern = Patterns.EMAIL_ADDRESS

            // 이메일 검사
            if(emailTxt.isEmpty()) {
                emailCheck = false
                binding.emailArea.error = "이메일주소를 입력하세요"
            } else if(emailPattern.matcher(emailTxt).matches()) {
                emailCheck = true
                binding.emailArea.error = null
            } else {
                emailCheck = false
                binding.emailArea.error = "이메일 형식이 잘못되었습니다"
            }

            // 비밀번호 검사
            if(pwTxt.isEmpty()) {
                pwCheck = false
                binding.pwArea.error = "비밀번호를 입력하세요"
            } else if (pwTxt.length<6) {
                pwCheck = false
                binding.pwArea.error = "최소 6자 이상 입력하세요"
            } else if (pwTxt.length>20) {
                pwCheck = false
                binding.pwArea.error = "20자 이하로 입력하세요"
            } else {
                pwCheck = true
                binding.pwArea.error = null
            }

            // 비밀번호 확인 검사
            if(pw2Txt.isEmpty()) {
                pw2Check = false
                binding.pw2Area.error = "비밀번호를 한 번 더 입력하세요"
            } else {
                pw2Check = true
                binding.pw2Area.error = null
            }

            // 비밀번호 일치 검사
            if(pw2Txt == pwTxt) {
                pwDoubleCheck = true
                binding.pw2Area.error = null
            } else {
                pwDoubleCheck = true
                binding.pw2Area.error = "비밀번호가 일치하지 않습니다"
            }

            // 회원가입 조건을 모두 만족하면
            if (allCheck) {

                // 회원가입
                auth.createUserWithEmailAndPassword(emailTxt, pwTxt)
                    .addOnCompleteListener(this) { task ->

                        // 성공하면
                        if (task.isSuccessful) {

                            // 메인 액티비티로 이동
                            val intent = Intent(this, MainActivity::class.java)

                            // 기존 스택을 비움 -> 뒤로가기 했을 때 이전 화면으로 되돌아가는 것 방지
                            finishAffinity()
                            startActivity(intent)

                        // 조건 만족해도
                        } else {

                            // 가입 불가능한 경우가 있음
                            Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()

                        }

                    }

            // 조건을 만족하지 못하면
            } else {

                // 가입 불가
                Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()

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