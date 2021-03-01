package com.wychua.ocbcapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.wychua.ocbcapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    companion object {
        const val TAG = "MainFragment"
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.btnTopup.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToTopupFragment()
            findNavController().navigate(action)
        }

        binding.btnTransfer.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToTransferFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModelFactory = MainViewModelFactory(MainFragmentArgs.fromBundle(requireArguments()).name)

        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        mainViewModel.loggedInUser.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                binding.txtWelcome.text = getString(R.string.welcome) + " " + user.name
                binding.txtBalanceValue.text = "$ " + String.format("%.2f", user.balance)
                binding.btnLogout.setOnClickListener{
                    mainViewModel.logout()
                }
            } else {
                findNavController().navigate(MainFragmentDirections.actionUnauthorised())
            }
        })
    }
}