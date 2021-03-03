package com.wychua.ocbcapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.wychua.ocbcapp.data.UserRepository
import com.wychua.ocbcapp.data.model.User
import com.wychua.ocbcapp.databinding.FragmentTopupBinding
import java.util.*

class TopUpFragment : Fragment() {
    companion object {
        const val TAG = "TopUpFragment"
    }

    private lateinit var binding: FragmentTopupBinding
    private lateinit var topUpViewModel: TopUpViewModel
    private lateinit var topUpViewModelFactory: TopUpViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topup, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topUpViewModelFactory =
            TopUpViewModelFactory((requireActivity().application as BankApplication).repository)
        topUpViewModel =
            ViewModelProvider(this, topUpViewModelFactory).get(TopUpViewModel::class.java)

        topUpViewModel.getUserByUserId(TopUpFragmentArgs.fromBundle(requireArguments()).userId)
            .observe(viewLifecycleOwner, Observer { users ->
                if (users != null && users.isNotEmpty()) {
                    topUpViewModel.setCurrentUser(users[0])
                }
            })

        binding.btnTopupAction.setOnClickListener {
            topUpViewModel.doTopUpTransaction(binding.editTopupValue.text.toString().toDouble())

            binding.btnTopupAction.isEnabled = false
            binding.editTopupValue.isEnabled = false

            Toast.makeText(
                requireContext(),
                "${topUpViewModel.currentUser.value?.name} top up $ ${
                    String.format(
                        "%.2f",
                        binding.editTopupValue.text.toString().toDouble()
                    )
                }",
                Toast.LENGTH_SHORT
            ).show()

            Log.i(
                TAG,
                "${topUpViewModel.currentUser.value?.name} top up $ ${
                    String.format(
                        "%.2f",
                        binding.editTopupValue.text.toString().toDouble()
                    )
                }"
            )
        }

        topUpViewModel.topupComplete.observe(viewLifecycleOwner, Observer { complete ->
            if (complete) {
                findNavController().popBackStack(R.id.mainFragment, false)
            }
        })
    }


}