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

    private val TAG = "SplashActivity"

    // 파이어베이스 인스턴스 선언
    private lateinit var auth: FirebaseAuth

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivitySplashBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        // 파이어베이스 인스턴스 초기화
        auth = Firebase.auth

        super.onCreate(savedInstanceState)

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivitySplashBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 현재 사용자의 uid
        val uid = auth.currentUser?.uid

            // 로그아웃 상태면
            if(uid == null) {

                // Handler() is deprecated
                // -> 생성자로 Looper.getMainLooper() 넣어주면 됨
                Handler(Looper.getMainLooper()).postDelayed({

                    // 인트로 액티비티로 이동
                    val intent = Intent(this, IntroActivity::class.java)

                    // 애니메이션 무시 and 스택 쌓임 방지
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION and Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(intent)
                    finish()

                }, 2000)

            // 로그인 한 상태면
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

    // 액티비티 파괴시
    override fun onDestroy() {

        // 바인딩 클래스 인스턴스 참조를 정리
        // -> 메모리 효율이 좋아짐
        vBinding = null
        super.onDestroy()

    }

}