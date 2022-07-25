package com.shinyelee.my_solo_life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.auth.IntroActivity
import com.shinyelee.my_solo_life.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // firebase
    private lateinit var auth: FirebaseAuth

    // viewBinding
    private var vBinding : ActivityMainBinding? = null
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        // firebase
        auth = Firebase.auth

        super.onCreate(savedInstanceState)

        // viewBinding
        vBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그아웃 버튼 클릭하면
        binding.logoutBtn.setOnClickListener {

            // 로그아웃 실행
            auth.signOut()
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

            // 스택에 쌓인 액티비티 종료하고 인트로 액티비티로 이동
            val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }

    }

    override fun onDestroy() {
        vBinding = null
        super.onDestroy()
    }

}