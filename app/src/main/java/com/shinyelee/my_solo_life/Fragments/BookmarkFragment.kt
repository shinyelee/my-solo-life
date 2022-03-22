package com.shinyelee.my_solo_life.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.FragmentBookmarkBinding

class BookmarkFragment : Fragment() {

    // binding
    private lateinit var binding : FragmentBookmarkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark, container, false)
        // bookmark -> home
        binding.homeT.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_homeFragment)
        }
        // bookmark -> tip
        binding.tipT.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_tipFragment)
        }
        // bookmark -> talk
        binding.talkT.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_talkFragment)
        }
        // bookmark -> store
        binding.storeT.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_storeFragment)
        }
        return binding.root

    }

}