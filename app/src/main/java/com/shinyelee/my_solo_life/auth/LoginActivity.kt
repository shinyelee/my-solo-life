package com.shinyelee.my_solo_life.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.MainActivity
import com.shinyelee.my_solo_life.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    // firebase
    private lateinit var auth: FirebaseAuth

    // viewBinding
    private var vBinding : ActivityLoginBinding? = null
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // firebase
        auth = Firebase.auth

        // viewBinding
        vBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    override fun onDestroy() {
        vBinding = null
        super.onDestroy()
    }

}