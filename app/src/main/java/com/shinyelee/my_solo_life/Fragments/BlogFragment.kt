package com.shinyelee.my_solo_life.Fragments

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

    private val TAG = BlogFragment::class.java.simpleName

    // (전역변수) 바인딩 객체 선언
    private var vBinding : FragmentBlogBinding? = null

    // 매번 null 확인 귀찮음 -> 바인딩 변수 재선언
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 뷰바인딩
        vBinding = FragmentBlogBinding.inflate(inflater, container, false)

        // 블로그 아이콘 클릭하면
        binding.cate1.setOnClickListener {

            // 콘텐츠리스트 액티비티로 이동
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("cate", "cate1")
            startActivity(intent)

        }

        binding.cate2.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("cate", "cate2")
            startActivity(intent)
        }

        // 홈 탭 클릭하면
        binding.homeT.setOnClickListener {

            // 홈 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_blogFragment_to_homeFragment)

        }

        // 토크 탭 클릭하면
        binding.talkT.setOnClickListener {

            // 토크 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_blogFragment_to_talkFragment)

        }

        // 북마크 탭 클릭하면
        binding.bookmarkT.setOnClickListener {

            // 북마크 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_blogFragment_to_bookmarkFragment)

        }

        // 깃허브 탭 클릭하면
        binding.githubT.setOnClickListener {

            // 깃허브 프래그먼트로 이동
            it.findNavController().navigate(R.id.action_blogFragment_to_githubFragment)

        }

        return binding.root

    }

}