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

            // 명시적 인텐트
            // -> 다른 액티비티 호출
            val intent = Intent(this, IntroActivity::class.java)

            // FLAG_ACTIVITY_NO_ANIMATION -> 액티비티 실행시 화면 전환 효과(좌우 슬라이드) 무시(스플래시 화면에 많이 씀)
            // FLAG_ACTIVITY_NO_HISTORY -> 액티비티를 스택에 쌓지 않음 -> 다른 액티비티로 이동시 사라짐(스플래시 화면에 많이 씀)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION and Intent.FLAG_ACTIVITY_NO_HISTORY)

            // 인트로 액티비티 시작
            startActivity(intent)

            // 스플래시 액티비티 종료
            finish()

        // 2초 지나면 이동
        }, 2000)

    }

}