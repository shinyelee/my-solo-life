package com.shinyelee.my_solo_life.contentsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shinyelee.my_solo_life.databinding.ActivityContentsShowBinding

class ContentsShowActivity : AppCompatActivity() {

    // viewBinding
    private var vBinding : ActivityContentsShowBinding? = null
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // viewBinding
        vBinding = ActivityContentsShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ContentsShowActivity -> url 값 받아옴
        val getUrl = intent.getStringExtra("url")
        binding.webView.loadUrl(getUrl.toString())

    }
}