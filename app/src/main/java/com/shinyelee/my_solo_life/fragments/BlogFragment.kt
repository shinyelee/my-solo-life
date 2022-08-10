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
import com.shinyelee.my_solo_life.databinding.FragmentBlogBinding

class BlogFragment : Fragment() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : FragmentBlogBinding? = null

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
        vBinding = FragmentBlogBinding.inflate(inflater, container, false)

        // 안드로이드 아이콘 클릭하면
        binding.androidIcon.setOnClickListener {

            // 명시적 인텐트
            // -> 다른 액티비티 호출
            val intent = Intent(context, ContentsListActivity::class.java)

            // android_studio 카테고리로 데이터 넘겨줌
            intent.putExtra("category", "android_studio")

            // 컨텐츠리스트 액티비티 시작
            startActivity(intent)

        }

        // 코틀린 아이콘 클릭하면
        binding.kotlinIcon.setOnClickListener {

            // 명시적 인텐트
            // -> 다른 액티비티 호출
            val intent = Intent(context, ContentsListActivity::class.java)

            // kotlin_syntax 카테고리로 데이터 넘겨줌
            intent.putExtra("category", "kotlin_syntax")

            // 컨텐츠리스트 액티비티 시작
            startActivity(intent)

        }

        // 에러 아이콘 클릭하면
        binding.errorIcon.setOnClickListener {

            // 명시적 인텐트
            // -> 다른 액티비티 호출
            val intent = Intent(context, ContentsListActivity::class.java)

            // error_warning 카테고리로 데이터 넘겨줌
            intent.putExtra("category", "error_warning")

            // 컨텐츠리스트 액티비티 시작
            startActivity(intent)

        }

        // 버전컨트롤시스템 아이콘 클릭하면
        binding.vcsIcon.setOnClickListener {

            // 명시적 인텐트
            // -> 다른 액티비티 호출
            val intent = Intent(context, ContentsListActivity::class.java)

            // vcs_github 카테고리로 데이터 넘겨줌
            intent.putExtra("category", "vcs_github")

            // 컨텐츠리스트 액티비티 시작
            startActivity(intent)

        }

        // 웹 아이콘 클릭하면
        binding.webIcon.setOnClickListener {

            // 명시적 인텐트
            // -> 다른 액티비티 호출
            val intent = Intent(context, ContentsListActivity::class.java)

            // web_internet 카테고리로 데이터 넘겨줌
            intent.putExtra("category", "web_internet")

            // 컨텐츠리스트 액티비티 시작
            startActivity(intent)

        }

        // 기타 아이콘 클릭하면
//        binding.etcIcon.setOnClickListener {
//
//            // 명시적 인텐트
//            // -> 다른 액티비티 호출
//            val intent = Intent(context, ContentsListActivity::class.java)
//
//            // etc 카테고리로 데이터 넘겨줌
//            intent.putExtra("category", "etc")
//
//            // 컨텐츠리스트 액티비티 시작
//            startActivity(intent)
//
//        }

        // 홈 아이콘 클릭하면
        binding.homeBtn.setOnClickListener {

            // 홈 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_blogFragment_to_homeFragment)

        }

        // 게시판 아이콘 클릭하면
        binding.boardBtn.setOnClickListener {

            // 게시판 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_blogFragment_to_boardFragment)

        }

        // 북마크 아이콘 클릭하면
        binding.bookmarkBtn.setOnClickListener {

            // 북마크 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_blogFragment_to_bookmarkFragment)

        }

        // 웹 아이콘 클릭하면
        binding.webBtn.setOnClickListener {

            // 웹 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_blogFragment_to_webFragment)

        }

        // 뷰바인딩
        return binding.root

    }

}