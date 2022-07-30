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

    // viewBinding
    private var vBinding : FragmentBlogBinding? = null
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // viewBinding
        vBinding = FragmentBlogBinding.inflate(inflater, container, false)

        // blog -> cate1
        binding.cate1.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("cate", "cate1")
            startActivity(intent)
        }
        // blog -> cate2
        binding.cate2.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("cate", "cate2")
            startActivity(intent)
        }

        // blog -> home
        binding.homeT.setOnClickListener {
            it.findNavController().navigate(R.id.action_blogFragment_to_homeFragment)
        }
        // blog -> talk
        binding.talkT.setOnClickListener {
            it.findNavController().navigate(R.id.action_blogFragment_to_talkFragment)
        }
        // blog -> bookmark
        binding.bookmarkT.setOnClickListener {
            it.findNavController().navigate(R.id.action_blogFragment_to_bookmarkFragment)
        }
        // blog -> github
        binding.githubT.setOnClickListener {
            it.findNavController().navigate(R.id.action_blogFragment_to_githubFragment)
        }
        return binding.root

    }

}