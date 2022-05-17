package com.aydinpolat.bitcointicker.presentation.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.databinding.FragmentRegisterBinding
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment

class RegisterFragment : BindingFragment<FragmentRegisterBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentRegisterBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}