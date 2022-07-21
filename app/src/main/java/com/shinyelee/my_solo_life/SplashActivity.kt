package com.shinyelee.my_solo_life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

            // 로그아웃 상태일 때
            if(auth.currentUser?.uid == null) {

                // Handler() is deprecated
                // 생성자로 Looper.getMainLooper() 넣어주면 됨
                Handler(Looper.getMainLooper()).postDelayed({

                    // 인트로 액티비티로 이동
                    val intent = Intent(this, IntroActivity::class.java)
                    // 애니메이션 무시 and 스택 쌓임 방지
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION and Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(intent)
                    finish()

                }, 2000)

            // 로그인 한 상태일 때
            } else {

                Handler(Looper.getMainLooper()).postDelayed({

                    // 메인 액티비티로 이동
                    val intent = Intent(this, MainActivity::class.java)
                    // 애니메이션 무시 and 스택 쌓임 방지
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION and Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(intent)
                    finish()

                }, 2000)

            }

    }

    override fun onDestroy() {
        vBinding = null
        super.onDestroy()
    }

}