package com.shinyelee.my_solo_life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.auth.IntroActivity

class SplashActivity : AppCompatActivity() {

    // firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // firebase
        auth = Firebase.auth
        if(auth.currentUser?.uid == null) {
            Log.d("Splash Activity", "null")
            // 스플래시 화면 -> 3초 뒤 사라짐
            // 로그인X -> 인트로 액티비티로 이동
            Handler().postDelayed({
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            }, 3000)
        } else {
            Log.d("Splash Activity", "not null")
            // 스플래시 화면 -> 3초 뒤 사라짐
            // 로그인O -> 메인 액티비티로 이동
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 3000)
        }

//        // 스플래시 화면 -> 3초 뒤 인트로 액티비티로 이동
//        Handler().postDelayed({
//            startActivity(Intent(this, IntroActivity::class.java))
//            finish()
//        }, 3000)

    }
}