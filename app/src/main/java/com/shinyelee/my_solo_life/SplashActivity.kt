package com.shinyelee.my_solo_life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.shinyelee.my_solo_life.auth.IntroActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Handler() is deprecated
        // -> 생성자로 Looper.getMainLooper() 넣어주면 됨
        Handler(Looper.getMainLooper()).postDelayed({

            // 인트로 액티비티 시작
            val intent = Intent(this, IntroActivity::class.java)

            // FLAG_ACTIVITY_NO_ANIMATION -> 화면 전환 효과(좌우 슬라이드) 무시
            // FLAG_ACTIVITY_NO_HISTORY -> 다른 액티비티로 이동시 스택에 쌓이지 않고 사라짐
            // 스플래시 화면에 많이 씀
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION and Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
            finish()

        // 2초 지나면 이동
        }, 2000)

    }

}