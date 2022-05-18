package com.aydinpolat.bitcointicker.presentation.fragment.coin_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem
import com.aydinpolat.bitcointicker.databinding.RowCoinItemBinding

class CoinListAdapter : PagingDataAdapter<CoinListItem, CoinListViewHolder>(COINS_DIFF_CALLBACK_) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        return CoinListViewHolder(
            RowCoinItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val COINS_DIFF_CALLBACK_ = object : DiffUtil.ItemCallback<CoinListItem>() {
            override fun areItemsTheSame(
                oldItem: CoinListItem,
                newItem: CoinListItem
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: CoinListItem,
                newItem: CoinListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}