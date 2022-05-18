package com.aydinpolat.bitcointicker.presentation.fragment.coin_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.databinding.FragmentCoinListBinding
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment
import com.aydinpolat.bitcointicker.presentation.fragment.coin_list.adapter.CoinListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinListFragment : BindingFragment<FragmentCoinListBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCoinListBinding::inflate

    private val coinListViewModel: CoinListViewModel by viewModels()
    private val coinListAdapter: CoinListAdapter by lazy { CoinListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeCoinsResult()
        binding.coinListFragmentRecyclerView.adapter = coinListAdapter
        getAllCoins()
    }

    private fun observeCoinsResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            coinListViewModel.coinListState.collect { coinListState ->
                if (coinListState.error != "") {
                    println("breakpoint")
                }
                binding.coinListFragmentProgressBar.isVisible = coinListState.isLoading ?: false
            }
        }
    }

    private fun getAllCoins() {
        lifecycleScope.launch {
            coinListViewModel.coinList.observe(viewLifecycleOwner) { paging ->
                lifecycleScope.launch {
                    coinListAdapter.submitData(paging)
                }
            }
        }
    }
}