package com.aydinpolat.bitcointicker.presentation.fragment.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.databinding.FragmentFavoritesBinding
import com.aydinpolat.bitcointicker.domain.model.CoinListItem
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment
import com.aydinpolat.bitcointicker.presentation.fragment.coin_list.adapter.CoinListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BindingFragment<FragmentFavoritesBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFavoritesBinding::inflate

    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private val coinListAdapter: CoinListAdapter by lazy { CoinListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesViewModel.getAllFavorites()
        setUpRecyclerView()
        listeners()
        favoriteCoinsState()
    }

    private fun setUpRecyclerView() {
        binding.favoriteFragmentRecyclerView.adapter = coinListAdapter
    }

    private fun listeners() {
        coinListAdapter.onItemClick = {
            findNavController().navigate(
                FavoritesFragmentDirections.actionFavoritesFragmentToCoinDetailFragment(
                    it
                )
            )
        }

        binding.favoriteFragmentCloseButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun favoriteCoinsState() {
        lifecycleScope.launch {
            favoritesViewModel.favoriteCoinsState.collect {
                if (it.favoriteCoins.isNotEmpty()) {
                    setListData(it.favoriteCoins)
                }
                if (it.isError) {
                    setListData(it.favoriteCoins)
                    binding.favoriteFragmentEmptyImage.isVisible = true
                    binding.favoriteFragmentErrorText.isVisible = true
                }
                binding.favoriteFragmentLoading.isVisible = it.isLoading
            }
        }
    }

    private fun setListData(favoriteCoins: List<CoinListItem>) {
        coinListAdapter.submitList(favoriteCoins)
        coinListAdapter.notifyDataSetChanged()
    }
}