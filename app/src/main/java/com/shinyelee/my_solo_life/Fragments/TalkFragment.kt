package com.shinyelee.my_solo_life.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.FragmentTalkBinding

class TalkFragment : Fragment() {

    // binding
    private lateinit var binding : FragmentTalkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)
        // talk -> home
        binding.homeT.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }
        // talk -> tip
        binding.tipT.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }
        // talk -> bookmark
        binding.bookmarkT.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)
        }
        // talk -> store
        binding.storeT.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)
        }
        return binding.root

    }

}