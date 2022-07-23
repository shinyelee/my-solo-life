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

    // firebase
    private lateinit var auth: FirebaseAuth

    // viewBinding
    private var vBinding : ActivityJoinBinding? = null
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // firebase
        auth = Firebase.auth

        // viewBinding
        vBinding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 회원가입 버튼 클릭하면
        binding.joinBtn.setOnClickListener {

            // 가입 조건 확인
            var emailCheck = true
            var pwCheck = true
            var pw2Check = true
            var allCheck = emailCheck and pwCheck and pw2Check

            // 이메일주소, 비밀번호, 비밀번호 확인, 별명
            val emailTxt = binding.email.text.toString()
            val pwTxt = binding.pw.text.toString()
            val pw2Txt = binding.pw2.text.toString()

            // 빈 칸 검사
            if (emailTxt.isEmpty() || pwTxt.isEmpty() || pw2Txt.isEmpty()) {
                allCheck = false
                Toast.makeText(this, "입력란을 모두 작성하세요", Toast.LENGTH_SHORT).show()
            }

            // 이메일주소 정규식
            val emailPattern = Patterns.EMAIL_ADDRESS

            // 이메일주소 검사
            if(emailTxt.isEmpty()) {
                emailCheck = false
                binding.emailArea.error = "이메일주소를 입력하세요"
            } else if(!emailPattern.matcher(emailTxt).matches()) {
                emailCheck = false
                binding.emailArea.error = "이메일 형식이 잘못되었습니다"
            } else {
                emailCheck = true
                binding.emailArea.error = null
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
            } else if(pwTxt != pw2Txt) {
                pw2Check = false
                binding.pw2Area.error = "비밀번호가 일치하지 않습니다"
            } else {
                pw2Check = true
                binding.pw2Area.error = null
            }

            // 가입 조건 모두 만족하면
            if (allCheck) {

                // 회원가입 실행
                auth.createUserWithEmailAndPassword(emailTxt, pwTxt)
                    .addOnCompleteListener(this) { task ->

                        // 가입 성공
                        if (task.isSuccessful) {

                            // 스택에 쌓인 액티비티 종료하고 메인 액티비티로 이동
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)

                        // 가입 실패
                        } else {

                            Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()

                        }

                    }

            } else {

                Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()

            }

        }

    }

    override fun onDestroy() {
        vBinding = null
        super.onDestroy()
    }

}