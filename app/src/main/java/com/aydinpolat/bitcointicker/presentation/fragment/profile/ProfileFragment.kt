package com.aydinpolat.bitcointicker.presentation.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.databinding.FragmentProfileBinding
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BindingFragment<FragmentProfileBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentProfileBinding::inflate

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.profileFragmentCloseButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.profileFragmentFavoriteButton.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToFavoritesFragment())
        }

        binding.profileFragmentLogOutButton.setOnClickListener {
            profileViewModel.logOut()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
        }
    }

}