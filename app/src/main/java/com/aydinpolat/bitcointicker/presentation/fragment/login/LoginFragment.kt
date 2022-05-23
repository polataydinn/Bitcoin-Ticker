package com.aydinpolat.bitcointicker.presentation.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.R
import com.aydinpolat.bitcointicker.common.LoadingDialog
import com.aydinpolat.bitcointicker.common.extentions.hideKeyboard
import com.aydinpolat.bitcointicker.common.extentions.showAlertDialog
import com.aydinpolat.bitcointicker.databinding.FragmentLoginBinding
import com.aydinpolat.bitcointicker.presentation.activity.MainActivity
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate

    private val loginViewModel: LoginViewModel by viewModels()
    private var loadingDialog: LoadingDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog((activity as MainActivity))
        bindUiToViewModel()
        collectUiEvents()
        collectStateFlow()
    }

    private fun collectStateFlow() {
        lifecycleScope.launchWhenCreated {
            loginViewModel.state.collect {
                bindViewModelToUi(it)
            }
        }
    }

    private fun bindViewModelToUi(state: LoginUiState) {
        binding.apply {
            if (loginFragmentEmailEdittext.text?.toString() != state.email) {
                loginFragmentEmailEdittext.setText(state.email)
            }
            if (loginFragmentPasswordEdittext.text?.toString() != state.password) {
                loginFragmentPasswordEdittext.setText(state.password)
            }
            when (state.isLoading) {
                true -> loadingDialog?.startLoadingDialog()
                false -> loadingDialog?.dismissDialog()
            }
        }
    }

    private fun collectUiEvents() {
        loginViewModel.uiEvent.observe(viewLifecycleOwner) {
            when (it) {
                is SignInUiEvent.Navigate -> {
                    (activity as MainActivity).hideKeyboard()
                    findNavController().apply {
                        navigate(it.direction)
                    }
                }
                is SignInUiEvent.ShowError -> {
                    showAlertDialog(getString(it.errorMessageResourceId), getString(R.string.error))
                }
            }
        }
    }

    private fun bindUiToViewModel() {
        binding.apply {
            loginFragmentEmailEdittext.addTextChangedListener {
                loginViewModel.onEmailChanged(it?.toString() ?: "")
            }
            loginFragmentPasswordEdittext.addTextChangedListener {
                loginViewModel.onPasswordChanged(it?.toString() ?: "")
            }
            loginFragmentLoginButton.setOnClickListener {
                loginViewModel.onLoginClicked()
            }
            loginFragmentRegisterButton.setOnClickListener {
                loginViewModel.onRegisterClicked()
            }
        }
    }

}