package com.aydinpolat.bitcointicker.domain.repository

import com.aydinpolat.bitcointicker.data.remote.model.AuthenticationResult
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem
import com.aydinpolat.bitcointicker.data.remote.model.UserDto
import com.aydinpolat.bitcointicker.domain.model.User

interface FirebaseRepository {
    suspend fun login(email: String, password: String, completeEvent: (AuthenticationResult) -> Unit)
    suspend fun logout()
    suspend fun signUp(userDto: UserDto, completeEvent: (AuthenticationResult) -> Unit)
    suspend fun saveUserToFirestore(user: User)
    suspend fun getUserName(completeEvent: (User) -> Unit)
    suspend fun setFavoriteCoin(coinDetail: CoinListItem, completeEvent: (Boolean) -> Unit)
    suspend fun getFavoriteCoins(favoriteCoins: (List<CoinListItem>) -> Unit)
    suspend fun removeFavoriteIcon(id: String)
    suspend fun isCoinFavorite(id: String, checkResult: (Boolean) -> Unit)
    fun isUserLoggedIn(): Boolean
}