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
        vBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.settingBtn.setOnClickListener {

            auth.signOut()
            Toast.makeText(this, "로그아웃", Toast.LENGTH_LONG).show()

            val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }

    }

    override fun onDestroy() {
        vBinding = null
        super.onDestroy()
    }

}