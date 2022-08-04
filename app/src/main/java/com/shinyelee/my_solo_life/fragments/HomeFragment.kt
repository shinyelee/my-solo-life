package com.shinyelee.my_solo_life.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : FragmentHomeBinding? = null

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
        vBinding = FragmentHomeBinding.inflate(inflater, container, false)

        // 블로그 아이콘 클릭하면
        binding.blogBtn.setOnClickListener {

            // 블로그 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_homeFragment_to_blogFragment)

        }

        // 게시판 아이콘 클릭하면
        binding.boardBtn.setOnClickListener {

            // 게시판 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_homeFragment_to_boardFragment)

        }

        // 북마크 아이콘 클릭하면
        binding.bookmarkBtn.setOnClickListener {

            // 북마크 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_homeFragment_to_bookmarkFragment)

        }

        // 웹 아이콘 클릭하면
        binding.webBtn.setOnClickListener {

            // 웹 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_homeFragment_to_webFragment)

        }

        // 뷰바인딩
        return binding.root

    }

}