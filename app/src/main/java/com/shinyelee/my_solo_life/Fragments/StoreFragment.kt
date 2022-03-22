package com.shinyelee.my_solo_life.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {

    // binding
    private lateinit var binding : FragmentStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)
        // store -> home
        binding.homeT.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_homeFragment)
        }
        // store -> tip
        binding.tipT.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_tipFragment)
        }
        // store -> talk
        binding.talkT.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_talkFragment)
        }
        // store -> bookmark
        binding.bookmarkT.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_bookmarkFragment)
        }
        return binding.root

    }

}