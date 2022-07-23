package com.shinyelee.my_solo_life.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    // firebase
    private lateinit var auth: FirebaseAuth

    // viewBinding
    private var vBinding : ActivitySettingBinding? = null
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        // firebase
        auth = Firebase.auth

        super.onCreate(savedInstanceState)

        // viewBinding
        vBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}