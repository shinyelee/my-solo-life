package com.shinyelee.my_solo_life.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.board.BoardWriteActivity
import com.shinyelee.my_solo_life.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {

    // (전역변수) 바인딩 객체 선언
    private var vBinding : FragmentBoardBinding? = null

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
        vBinding = FragmentBoardBinding.inflate(inflater, container, false)

        // 글쓰기 버튼 클릭 -> 작성 액티비티로 이동
        binding.writeBtn.setOnClickListener {

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(context, BoardWriteActivity::class.java)

            // 글쓰기 액티비티 시작
            startActivity(intent)

        }

        // 홈 버튼 클릭 -> 홈 프래그먼트로 이동
        binding.homeBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_homeFragment)
        }

        // 블로그 버튼
        binding.blogBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_blogFragment)
        }

        // 북마크 버튼
        binding.bookmarkBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_bookmarkFragment)
        }

        // 웹 버튼
        binding.webBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_boardFragment_to_webFragment)
        }

        // 뷰바인딩
        return binding.root

    }

}