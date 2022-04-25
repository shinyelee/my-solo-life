package com.shinyelee.my_solo_life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.auth.IntroActivity
import com.shinyelee.my_solo_life.setting.SettingActivity

class MainActivity : AppCompatActivity() {

    // firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        // firebase
        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.settingBtn).setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

//        findViewById<Button>(R.id.logoutBtn).setOnClickListener {
//
//            // 로그아웃
//            auth.signOut()
//            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_LONG).show()
//            // 인트로 액티비티로 이동
//            val intent = Intent(this, IntroActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            startActivity(intent)
//
//        }

    }
}