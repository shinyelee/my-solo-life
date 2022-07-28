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
            Firebase.auth.signOut()
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

            // 인트로 액티비티로 이동
            val intent = Intent(this, IntroActivity::class.java)

            // 기존 스택을 비움 -> 뒤로가기 했을 때 이전 화면으로 되돌아가는 것 방지
//            finishAffinity()
            // 백스택 제거하고 새 액티비티 시작
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

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