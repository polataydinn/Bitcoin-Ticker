package com.aydinpolat.bitcointicker.presentation.fragment.profile

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.databinding.FragmentProfileBinding
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment

class ProfileFragment : BindingFragment<FragmentProfileBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentProfileBinding::inflate

}