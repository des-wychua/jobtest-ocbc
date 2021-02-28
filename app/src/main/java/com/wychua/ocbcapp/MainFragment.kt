package com.wychua.ocbcapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.wychua.ocbcapp.databinding.FragmentMainBinding

class MainFragment: Fragment() {
    companion object {
        const val TAG = "MainFragment"
    }

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTopup.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToTopupFragment()
            findNavController().navigate(action)
        }

        binding.btnTransfer.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToTransferFragment()
            findNavController().navigate(action)
        }
    }
}