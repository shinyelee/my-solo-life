package com.shinyelee.my_solo_life.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shinyelee.my_solo_life.databinding.FragmentGithubBinding

class GithubFragment : Fragment() {

    private val TAG = GithubFragment::class.java.simpleName

    // (전역변수) 바인딩 객체 선언
    private var vBinding : FragmentGithubBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // 뷰바인딩
        vBinding = FragmentGithubBinding.inflate(inflater, container, false)

        // URL
        binding.githubWebView.loadUrl("https://github.com/shinyelee")

        return binding.root

    }

}