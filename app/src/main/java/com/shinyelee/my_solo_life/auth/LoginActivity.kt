package com.shinyelee.my_solo_life.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.MainActivity
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.ActivityJoinBinding
import com.shinyelee.my_solo_life.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // firebase
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.loginBtn.setOnClickListener {
            val email = binding.emailArea.text.toString()
            val password = binding.passwordArea.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 로그인 성공
                        Toast.makeText(this, "로그인에 성공했습니다", Toast.LENGTH_LONG).show()
                        // 로그인 페이지 닫고 메인 페이지로 이동
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    } else {
                        // 로그인 실패
                        Toast.makeText(this, "로그인에 실패했습니다", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }
}