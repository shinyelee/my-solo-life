package com.shinyelee.my_solo_life.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shinyelee.my_solo_life.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {

    // viewBinding
    private var vBinding : FragmentStoreBinding? = null
    private val binding get() = vBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // viewBinding
        vBinding = FragmentStoreBinding.inflate(inflater, container, false)
        binding.storeWebView.loadUrl("https://www.inflearn.com/")
        return binding.root

    }

}