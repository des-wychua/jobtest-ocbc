package com.wychua.ocbcapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.wychua.ocbcapp.data.model.User
import com.wychua.ocbcapp.databinding.FragmentPayBinding
import com.wychua.ocbcapp.ui.adapter.CustomDropDownAdapter
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class PayFragment : Fragment() {
    companion object {
        const val TAG = "PayFragment"
    }

    private lateinit var binding: FragmentPayBinding
    private lateinit var payViewModel: PayViewModel
    private lateinit var payViewModelFactory: PayViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pay, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        payViewModelFactory =
            PayViewModelFactory((requireActivity().application as BankApplication).repository)
        payViewModel = ViewModelProvider(this, payViewModelFactory).get(PayViewModel::class.java)

        val userList = ArrayList<User>()

        // set custom drop down adapter for spinner to use User object
        val adapter = CustomDropDownAdapter(requireContext(), userList)
        binding.spinnerUserlist.adapter = adapter

        // get user list from database and fill spinner items
        payViewModel.getOrderedUserList()
            .observe(viewLifecycleOwner, Observer<List<User>> { users ->
                for (user in users) {
                    if (user.userId != PayFragmentArgs.fromBundle(requireArguments()).userId) {
                        userList.add(user)
                        Log.i(TAG, "added ${user.name} to userList")
                    }
                    if (user.userId == PayFragmentArgs.fromBundle(requireArguments()).userId) {
                        payViewModel.setPayer(user)
                    }
                }
                adapter.notifyDataSetChanged()
            })

        binding.spinnerUserlist.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    payViewModel.setPayee(parent?.getItemAtPosition(position) as User)
                }

            }

        binding.btnPayAction.setOnClickListener {

            payViewModel.doPaymentTransaction(binding.editPayamount.text.toString().toDouble())

            binding.spinnerUserlist.isEnabled = false
            binding.editPayamount.isEnabled = false
            binding.btnPayAction.isEnabled = false

            Toast.makeText(
                requireContext(),
                "${payViewModel.payer.value!!.name} paid ${(binding.spinnerUserlist.selectedItem as User).name} $ ${
                    String.format("%.2f", binding.editPayamount.text.toString().toDouble())
                }", Toast.LENGTH_SHORT
            ).show()

            Log.i(
                TAG,
                "${payViewModel.payer.value!!.name} paid ${(binding.spinnerUserlist.selectedItem as User).name} $ ${
                    String.format("%.2f", binding.editPayamount.text.toString().toDouble())
                }"
            )
        }

        payViewModel.paymentComplete.observe(viewLifecycleOwner, Observer { complete ->
            if (complete) {
                findNavController().popBackStack(R.id.mainFragment, false)
            }
        })
    }
}
