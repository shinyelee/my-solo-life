package com.shinyelee.my_solo_life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.auth.IntroActivity
import com.shinyelee.my_solo_life.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 파이어베이스 인스턴스 선언
    private lateinit var auth: FirebaseAuth

    // (전역변수) 바인딩 객체 선언
    private var vBinding : ActivityMainBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 파이어베이스 인스턴스 초기화
        auth = Firebase.auth

        // 자동 생성된 뷰바인딩 클래스에서의 inflate 메서드 활용
        // -> 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        vBinding = ActivityMainBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부 최상위에 있는 뷰의 인스턴스 활용
        // -> 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 설정 버튼 클릭하면
        binding.settingBtn.setOnClickListener {

            // 로그아웃 실행
            Firebase.auth.signOut()

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(this, IntroActivity::class.java)

            // 인트로 액티비티 시작
            startActivity(intent)

            // 메인 액티비티 종료
            finish()

        }

    }

    // 액티비티 파괴시
    override fun onDestroy() {

        // 바인딩 클래스 인스턴스 참조를 정리 -> 메모리 효율이 좋아짐
        vBinding = null
        super.onDestroy()

    }

}