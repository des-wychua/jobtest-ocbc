package com.wychua.ocbcapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.wychua.ocbcapp.databinding.FragmentTopupBinding

class TopUpFragment: Fragment() {
    companion object {
        const val TAG = "TopUpFragment"
    }

    private lateinit var binding: FragmentTopupBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topup, container, false)
        return binding.root
    }
}