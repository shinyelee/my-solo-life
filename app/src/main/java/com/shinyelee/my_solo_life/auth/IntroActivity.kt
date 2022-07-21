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

    // firebase
    private lateinit var auth: FirebaseAuth

    // viewBinding
    private var vBinding : ActivityIntroBinding? = null
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        // firebase
        auth = Firebase.auth

        super.onCreate(savedInstanceState)

        // viewBinding
        vBinding = ActivityIntroBinding.inflate(layoutInflater)
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

            // 비회원으로 로그인
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        // 로그인 성공
                        Toast.makeText(this, "비회원으로 로그인되었습니다", Toast.LENGTH_LONG).show()

                        // 메인 액티비티로 이동
                        val intent = Intent(this, MainActivity::class.java)
                        // 인트로 액티비티 종료
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)

                    } else {

                        // 로그인 실패
                        Toast.makeText(this, "비회원 로그인에 실패했습니다", Toast.LENGTH_LONG).show()

                    }

                }

        }

    }

    override fun onDestroy() {
        vBinding = null
        super.onDestroy()
    }

}
