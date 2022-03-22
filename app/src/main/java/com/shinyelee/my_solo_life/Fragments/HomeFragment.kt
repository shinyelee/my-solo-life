package com.shinyelee.my_solo_life.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    // binding
    private lateinit var binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        // home -> tip
        binding.tipT.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_tipFragment)
        }
        // home -> talk
        binding.talkT.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)
        }
        // home -> bookmark
        binding.bookmarkT.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_bookmarkFragment)
        }
        // home -> store
        binding.storeT.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_storeFragment)
        }
        return binding.root
    }


}