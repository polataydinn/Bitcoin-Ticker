package com.aydinpolat.bitcointicker.presentation.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.databinding.FragmentLoginBinding
import com.aydinpolat.bitcointicker.presentation.activity.MainActivity
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment
import com.aydinpolat.bitcointicker.util.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate

    private val loginViewModel: LoginViewModel by viewModels()
    private var loadingDialog: LoadingDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog((activity as MainActivity))
        buttonListeners()
        subscribeObservables()
    }

    private fun buttonListeners() {
        binding.loginFragmentRegisterButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.loginFragmentLoginButton.setOnClickListener {
            val email = binding.loginFragmentEmailEdittext.text.toString()
            val password = binding.loginFragmentPasswordEdittext.text.toString()
            loginViewModel.checkIfInputsAreValid(email, password)
        }
    }

    private fun subscribeObservables() {
        loginViewModel.isUserLoggedIn.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCoinListFragment())
            }
        }

        loginViewModel.emailFormatResult.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }

        loginViewModel.passwordFormatResult.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.resultMessage, Toast.LENGTH_SHORT).show()
        }

        loginViewModel.loadingResult.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    loadingDialog?.startLoadingDialog()
                }
                false -> {
                    loadingDialog?.dismissDialog()
                }
            }
        }
    }
}