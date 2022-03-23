package com.shinyelee.my_solo_life.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.contentsList.ContentsListActivity
import com.shinyelee.my_solo_life.databinding.FragmentTipBinding

class TipFragment : Fragment() {

    // binding
    private lateinit var binding : FragmentTipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tip, container, false)

        // tip -> cate1
        binding.cate1.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            startActivity(intent)
        }

        // tip -> home
        binding.homeT.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_homeFragment)
        }
        // tip -> talk
        binding.talkT.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_talkFragment)
        }
        // tip -> bookmark
        binding.bookmarkT.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_bookmarkFragment)
        }
        // tip -> store
        binding.storeT.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_storeFragment)
        }
        return binding.root

    }

}