package com.aydinpolat.bitcointicker.presentation.fragment.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.databinding.FragmentFavoritesBinding
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment

class FavoritesFragment : BindingFragment<FragmentFavoritesBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFavoritesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}