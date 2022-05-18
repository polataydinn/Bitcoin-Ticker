package com.aydinpolat.bitcointicker.presentation.fragment.coin_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem
import com.aydinpolat.bitcointicker.databinding.FragmentCoinListBinding
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment
import com.aydinpolat.bitcointicker.presentation.fragment.coin_list.adapter.CoinListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinListFragment : BindingFragment<FragmentCoinListBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCoinListBinding::inflate

    private val coinListViewModel: CoinListViewModel by viewModels()
    private val coinListAdapter: CoinListAdapter by lazy { CoinListAdapter() }
    private var listOfCoins = arrayListOf<CoinListItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewSetup()
        listeners()
        getAllCoins()
        observeCoinsResult()
        observeSearchResult()
    }

    private fun listeners() {
        binding.coinListFragmentSearchEdittext.addTextChangedListener {
            coinListViewModel.setSearchQuery(it.toString())
        }
    }

    private fun recyclerViewSetup() {
        binding.coinListFragmentRecyclerView.adapter = coinListAdapter
        coinListAdapter.submitList(listOfCoins)
    }

    private fun observeSearchResult() {
        coinListViewModel.searchResult.observe(viewLifecycleOwner) {
            clearListOfCoins()
            addListOfCoins(it)
        }
    }

    private fun observeCoinsResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            coinListViewModel.coinListState.collect { coinListState ->
                if (coinListState.error != "") {
                    binding.coinListFragmentErrorText.apply {
                        visibility = View.VISIBLE
                        text = coinListState.error
                    }
                }
                binding.coinListFragmentProgressBar.isVisible = coinListState.isLoading ?: false
            }
        }
    }

    private fun getAllCoins() {
        coinListViewModel.coinList.observe(viewLifecycleOwner) {
            addListOfCoins(it)
        }
    }

    private fun clearListOfCoins() {
        listOfCoins.clear()
        coinListAdapter.notifyDataSetChanged()
    }

    private fun addListOfCoins(listCoin: List<CoinListItem>) {
        listOfCoins.addAll(listCoin)
        coinListAdapter.notifyDataSetChanged()
    }

}