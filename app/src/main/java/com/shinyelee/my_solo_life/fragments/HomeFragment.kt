package com.shinyelee.my_solo_life.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.contentsList.ContentsListActivity
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

        // 안드로이드 아이콘 클릭하면
        binding.androidIcon.setOnClickListener {

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(context, ContentsListActivity::class.java)

            // android_studio 카테고리로 데이터 넘겨줌
            intent.putExtra("category", "android_studio")

            // 컨텐츠리스트 액티비티 시작
            startActivity(intent)

        }

        // 코틀린 아이콘 -> kotlin_syntax
        binding.kotlinIcon.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category", "kotlin_syntax")
            startActivity(intent)
        }

        // 에러 아이콘 -> error_warning
        binding.errorIcon.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category", "error_warning")
            startActivity(intent)
        }

        // 버전컨트롤시스템 아이콘 -> vcs_github
        binding.vcsIcon.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category", "vcs_github")
            startActivity(intent)
        }

        // 웹 아이콘 -> category
        binding.webIcon.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category", "web_internet")
            startActivity(intent)
        }

        // 기타 아이콘 -> etc (추후 수정)
//        binding.etcIcon.setOnClickListener {}

        // 블로그 버튼 클릭 -> 블로그 프래그먼트로 이동
        binding.blogBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_blogFragment)
        }

        // 게시판 버튼
        binding.boardBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_boardFragment)
        }

        // 북마크 버튼
        binding.bookmarkBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_bookmarkFragment)
        }

        // 웹 버튼
        binding.webBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_webFragment)
        }

        // 뷰바인딩
        return binding.root

    }

}