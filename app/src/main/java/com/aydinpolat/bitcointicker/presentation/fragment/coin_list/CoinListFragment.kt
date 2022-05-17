package com.aydinpolat.bitcointicker.presentation.fragment.coin_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.databinding.FragmentCoinListBinding
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment

class CoinListFragment : BindingFragment<FragmentCoinListBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCoinListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}