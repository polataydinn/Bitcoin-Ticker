package com.aydinpolat.bitcointicker.presentation.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.R
import com.aydinpolat.bitcointicker.common.extentions.showAlertDialog
import com.aydinpolat.bitcointicker.databinding.FragmentRegisterBinding
import com.aydinpolat.bitcointicker.presentation.activity.MainActivity
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment
import com.aydinpolat.bitcointicker.common.LoadingDialog
import com.aydinpolat.bitcointicker.common.extentions.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterFragment : BindingFragment<FragmentRegisterBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentRegisterBinding::inflate

    private val registerViewModel: RegisterViewModel by viewModels()
    private var loadingDialog: LoadingDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog((activity as MainActivity))
        bindUiToViewModel()
        collectStateFlow()
        collectUiEvents()
    }

    private fun collectUiEvents() {
        registerViewModel.uiEvent.observe(viewLifecycleOwner) {
            when (it) {
                is RegisterUiEvent.Navigate -> {
                    (activity as MainActivity).hideKeyboard()
                    findNavController().apply {
                        navigate(it.direction)
                    }
                }
                is RegisterUiEvent.ShowError -> {
                    showAlertDialog(getString(it.errorMessageResourceId), getString(R.string.error))
                }
            }
        }
    }

    private fun collectStateFlow() {
        lifecycleScope.launchWhenCreated {
            registerViewModel.state.collect {
                bindViewModelToUi(it)
            }
        }
    }

    private fun bindViewModelToUi(state: RegisterUiState) {
        binding.apply {
            if (registerFragmentEmailEdittext.text?.toString() != state.email) {
                registerFragmentEmailEdittext.setText(state.email)
            }
            if (registerFragmentPasswordEdittext.text?.toString() != state.password) {
                registerFragmentPasswordEdittext.setText(state.password)
            }
            when (state.isLoading) {
                true -> loadingDialog?.startLoadingDialog()
                false -> loadingDialog?.dismissDialog()
            }
        }
    }

    private fun bindUiToViewModel() {
        binding.apply {
            registerFragmentEmailEdittext.addTextChangedListener {
                registerViewModel.onEmailChanged(it?.toString() ?: "")
            }
            registerFragmentPasswordEdittext.addTextChangedListener {
                registerViewModel.onPasswordChanged(it?.toString() ?: "")
            }
            registerFragmentRegisterButton.setOnClickListener {
                registerViewModel.onRegisterClicked()
            }
            registerFragmentCloseButton.setOnClickListener {
                registerViewModel.onSignInClicked()
            }
        }
    }
}