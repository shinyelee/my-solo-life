package com.shinyelee.my_solo_life.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.auth.IntroActivity

class SettingActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        auth = Firebase.auth

        // 둘 다 같음
//        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        val logoutBtn : Button = findViewById<Button>(R.id.logoutBtn)

        logoutBtn.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_LONG).show()

            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }

    }

}