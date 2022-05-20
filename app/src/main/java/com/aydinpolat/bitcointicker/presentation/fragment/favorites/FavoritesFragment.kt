package com.aydinpolat.bitcointicker.presentation.fragment.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.aydinpolat.bitcointicker.databinding.FragmentFavoritesBinding
import com.aydinpolat.bitcointicker.presentation.binding_adapter.BindingFragment
import com.aydinpolat.bitcointicker.presentation.fragment.coin_list.adapter.CoinListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BindingFragment<FragmentFavoritesBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFavoritesBinding::inflate

    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private val coinListAdapter: CoinListAdapter by lazy { CoinListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        favoritesViewModel.getAllFavorites()

        coinListAdapter.onItemClick = {
            findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToCoinDetailFragment(it))
        }

        binding.favoriteFragmentCloseButton.setOnClickListener {
            findNavController().navigateUp()
        }

        favoritesViewModel.favoriteCoins.observe(viewLifecycleOwner) {
            coinListAdapter.submitList(it)
            coinListAdapter.notifyDataSetChanged()
        }

        favoritesViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.favoriteFragmentLoading.isVisible = it
        }

        favoritesViewModel.isError.observe(viewLifecycleOwner) {
            binding.favoriteFragmentErrorText.isVisible = it
        }
    }

    private fun setUpRecyclerView() {
        binding.favoriteFragmentRecyclerView.adapter = coinListAdapter
    }

}