package com.shinyelee.my_solo_life.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.MainActivity
import com.shinyelee.my_solo_life.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

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

        binding.joinBtn.setOnClickListener {
            var isGoToJoin = true
            val email = binding.emailArea.text.toString()
            val password1 = binding.passwordArea1.text.toString()
            val password2 = binding.passwordArea2.text.toString()
            // 값이 비어있는지 확인
            if (email.isEmpty()) {
                Toast.makeText(this, "이메일란을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (password1.isEmpty()) {
                Toast.makeText(this, "비밀번호란을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (password2.isEmpty()) {
                Toast.makeText(this, "비밀번호 확인란을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            // 비밀번호 일치 여부 확인
            if (!password1.equals(password2)) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (password1.length < 6) {
                Toast.makeText(this, "비밀번호를 6자리 이상으로 설정해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            // 회원가입 실행
            if (isGoToJoin) {
                auth.createUserWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // 가입 성공
                            Toast.makeText(this, "회원가입이 완료되었습니다", Toast.LENGTH_LONG).show()
                            // 가입 페이지 닫고 메인 페이지로 이동
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                        } else {
                            // 가입 실패
                            Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

    }

}