package com.wychua.ocbcapp.ui.login

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.wychua.ocbcapp.*

import com.wychua.ocbcapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginViewModelFactory: LoginViewModelFactory

    companion object {
        val TAG = "LoginFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // If the user presses the back button, bring them back to the home screen
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        loginViewModelFactory =
            LoginViewModelFactory((requireActivity().application as BankApplication).repository)

        loginViewModel = ViewModelProvider(this, loginViewModelFactory)
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                binding.btnLogin.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    binding.editUsername.error = getString(it)
                }
            })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                binding.loading.visibility = View.GONE
                loginResult.error?.let {
                    showLoginFailed(it)
                    Log.i(TAG, "login failed")
                }
                loginResult.success?.let {
                    findNavController().navigate(
                        LoginFragmentDirections.actionAuthorised(
                            balance = it.balance.toFloat(),
                            name = it.name,
                            userId = it.userId
                        )
                    )
                    Log.i(TAG, "login success")
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    binding.editUsername.text.toString()
                )
            }
        }
        binding.editUsername.addTextChangedListener(afterTextChangedListener)
        binding.editUsername.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login(binding.editUsername.text.toString())
            }
            false
        }

        binding.btnLogin.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            login(binding.editUsername.text.toString())
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    private fun login(name: String) {
        loginViewModel.getUserByName(name).observe(viewLifecycleOwner, Observer { users ->
            if (users != null && users.isNotEmpty()) {
                loginViewModel.login(users[0])
            } else {
                loginViewModel.register(name)
            }
        })
    }
}