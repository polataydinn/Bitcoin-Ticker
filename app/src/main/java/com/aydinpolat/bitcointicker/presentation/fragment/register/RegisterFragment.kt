package com.aydinpolat.bitcointicker.presentation.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.data.model.User
import com.aydinpolat.bitcointicker.databinding.FragmentRegisterBinding
import com.aydinpolat.bitcointicker.presentation.activity.MainActivity
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment
import com.aydinpolat.bitcointicker.util.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BindingFragment<FragmentRegisterBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentRegisterBinding::inflate

    private val registerViewModel: RegisterViewModel by viewModels()
    private var loadingDialog: LoadingDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog((activity as MainActivity))

        binding.registerFragmentCloseButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.registerFragmentRegisterButton.setOnClickListener {
            val email = binding.registerFragmentEmailEdittext.text.toString().trim()
            val password = binding.registerFragmentPasswordEdittext.text.toString().trim()
            val fullName = binding.registerFragmentFullNameEdittext.text.toString()
            registerViewModel.checkIfInputsAreValid(User(email, password, fullName))
        }

        registerViewModel.loadingResult.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    loadingDialog?.startLoadingDialog()
                }
                false -> {
                    loadingDialog?.dismissDialog()
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToCoinListFragment())
                }
            }
        }

        registerViewModel.emailFormatResult.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }

        registerViewModel.passwordFormatResult.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }

        registerViewModel.signUpResult.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.resultMessage, Toast.LENGTH_SHORT).show()
        }
    }
}