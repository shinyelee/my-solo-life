package com.shinyelee.my_solo_life.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.FragmentWebBinding

class WebFragment : Fragment() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : FragmentWebBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        // 뷰바인딩
        vBinding = FragmentWebBinding.inflate(inflater, container, false)

        // 웹뷰에 깃허브 링크 넣음
        binding.webView.loadUrl("https://github.com/shinyelee")

        // 홈 버튼 클릭 -> 홈 프래그먼트로 이동
        binding.homeBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_webFragment_to_homeFragment)
        }

        // 블로그 버튼
        binding.blogBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_webFragment_to_blogFragment)
        }

        // 게시판 버튼
        binding.boardBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_webFragment_to_boardFragment)
        }

        // 북마크 버튼
        binding.bookmarkBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_webFragment_to_bookmarkFragment)
        }

        // 뷰바인딩
        return binding.root

    }

}