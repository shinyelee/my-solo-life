package com.shinyelee.my_solo_life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.auth.IntroActivity
import com.shinyelee.my_solo_life.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    // firebase
    private lateinit var auth: FirebaseAuth

    // viewBinding
    private var vBinding : ActivitySplashBinding? = null
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        // firebase
        auth = Firebase.auth

        super.onCreate(savedInstanceState)

        // viewBinding
        vBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

            // 로그인 안 한 상태일 때
            if(auth.currentUser?.uid == null) {
                // 인트로 액티비티로 이동
                Handler().postDelayed({
                    startActivity(Intent(this, IntroActivity::class.java))
                    finish()
                }, 2000)
            // 로그인 한 상태일 때
            } else {
                // 메인 액티비티로 이동
                Handler().postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 2000)
            }

    }
}