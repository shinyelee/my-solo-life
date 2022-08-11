package com.shinyelee.my_solo_life.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.FragmentBookmarkBinding

class BookmarkFragment : Fragment() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : FragmentBookmarkBinding? = null

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
        vBinding = FragmentBookmarkBinding.inflate(inflater, container, false)

        // 홈 버튼 클릭하면
        binding.homeBtn.setOnClickListener {

            // 홈 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_webFragment_to_homeFragment)

        }

        // 블로그 버튼 -> 블로그 프래그먼트
        binding.blogBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_webFragment_to_blogFragment)
        }

        // 게시판 버튼 -> 게시판 프래그먼트
        binding.boardBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_boardFragment)
        }

        // 웹 버튼 -> 웹 프래그먼트
        binding.webBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_webFragment)
        }

        // 뷰바인딩
        return binding.root

    }

}