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
import com.shinyelee.my_solo_life.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    // firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // firebase
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)
        // 로그인 버튼 클릭 -> 로그인 액티비티로 이동
        binding.loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        // 회원가입 버튼 클릭 -> 조인 액티비티로 이동
        binding.joinBtn.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
        // 비회원 로그인
        binding.guestBtn.setOnClickListener {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 비회원 로그인 성공
                        Toast.makeText(this, "비회원으로 로그인되었습니다", Toast.LENGTH_LONG).show()
                        // 로그인 페이지 닫고 메인 페이지로 이동
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    } else {
                        // 로그인 실패
                        Toast.makeText(this, "비회원 로그인에 실패했습니다", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }
}
