package com.aydinpolat.bitcointicker.domain.use_case.get_paged_coins

import androidx.paging.PagingSource
import com.aydinpolat.bitcointicker.common.Constants.PAGE_SIZE
import com.aydinpolat.bitcointicker.data.local.CoinDao
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem

class GetPagedCoinsSource(
    private val coinDao: CoinDao
) : PagingSource<Int, CoinListItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinListItem> {
        val nextPage = params.key ?: 0
        return try {
            val coinPage = coinDao.getCoinsPaged(nextPage, PAGE_SIZE)
            LoadResult.Page(
                data = coinPage,
                prevKey = null,
                nextKey = if (coinPage.isEmpty()) null else nextPage + 4
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}