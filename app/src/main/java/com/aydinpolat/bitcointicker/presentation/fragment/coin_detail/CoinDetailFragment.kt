package com.aydinpolat.bitcointicker.presentation.fragment.coin_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.databinding.FragmentCoinDetailBinding
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment

class CoinDetailFragment : BindingFragment<FragmentCoinDetailBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCoinDetailBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}