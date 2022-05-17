package com.aydinpolat.bitcointicker.presentation.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.R
import com.aydinpolat.bitcointicker.databinding.FragmentLoginBinding
import com.aydinpolat.bitcointicker.presentation.activity.MainActivity
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginFragment : BindingFragment<FragmentLoginBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideOrShowBottomNavigation(false)
    }
}