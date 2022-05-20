package com.aydinpolat.bitcointicker.presentation.fragment.coin_list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem
import com.aydinpolat.bitcointicker.databinding.RowCoinItemBinding

class CoinListViewHolder(private val rowCoinItemBinding: RowCoinItemBinding) :
    RecyclerView.ViewHolder(rowCoinItemBinding.root) {

    fun bind(coinListItem: CoinListItem, onItemClick: ((String) -> Unit)?) {
        rowCoinItemBinding.apply {
            coinIdTextiew.text = coinListItem.id
            coinNameTextview.text = coinListItem.name
            coinSymbolTextview.text = coinListItem.symbol

            root.setOnClickListener {
                onItemClick?.invoke(coinListItem.id)
            }
        }
    }
}
