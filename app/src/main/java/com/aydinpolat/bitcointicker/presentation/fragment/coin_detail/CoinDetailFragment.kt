package com.aydinpolat.bitcointicker.presentation.fragment.coin_detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.data.remote.model.CoinDetail
import com.aydinpolat.bitcointicker.databinding.FragmentCoinDetailBinding
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinDetailFragment : BindingFragment<FragmentCoinDetailBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCoinDetailBinding::inflate

    private val coinDetailViewModel: CoinDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        getCoinDetail()
    }

    private fun setListeners() {
        binding.detailFragmentCloseButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun getCoinDetail() {
        lifecycleScope.launch {
            coinDetailViewModel.coinDetailState.collect { coinDetailState ->
                if (coinDetailState.coinDetail != null) {
                    setCoinToView(coinDetailState.coinDetail)
                }
                if (coinDetailState.error != "") {
                    binding.detailFragmentErrorMessage.apply {
                        text = coinDetailState.error
                        visibility = View.VISIBLE
                    }
                }
                binding.detailFragmentLoadingBar.isVisible = coinDetailState.isLoading ?: false
            }
        }
    }

    private fun setCoinToView(coinDetail: CoinDetail) {
        val coinPrice = coinDetail.marketData.currentPrice.usd.toString()
        binding.apply {
            detailFragmentCoinName.text = coinDetail.name
            detailFragmentCoinHash.text = coinDetail.hashingAlgorithm
            detailFragmentCoinPrice.text = "$coinPrice$"
            detailFragmentCoinSymbol.text = coinDetail.symbol
            detailFragmentCoinChangeResult.text =
                coinDetail.marketData.priceChangePercentage24h.toString()
            detailFragmentChangeInHoursText.isVisible = true

            Glide.with(binding.root)
                .load(coinDetail.image.large)
                .into(detailFragmentCoinImage)
        }
        convertHtml(coinDetail.description.en)
    }

    private fun convertHtml(url: String?) {
        val toHtml = url?.replace("\\n", "<br />")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.detailFragmentCoinDescription.text =
                Html.fromHtml(toHtml, Html.FROM_HTML_MODE_COMPACT)
        } else {
            binding.detailFragmentCoinDescription.text = url
        }
    }

}