package com.aydinpolat.bitcointicker.data.repository

import com.aydinpolat.bitcointicker.common.Constants.FAVORITES
import com.aydinpolat.bitcointicker.common.Constants.USERS
import com.aydinpolat.bitcointicker.data.remote.model.toDomain
import com.aydinpolat.bitcointicker.domain.auth.UserProxy
import com.aydinpolat.bitcointicker.domain.model.CoinListItem
import com.aydinpolat.bitcointicker.domain.repository.FirebaseRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseRepositoryImplementation constructor(
    private val userProxy: UserProxy
) : FirebaseRepository {

    override suspend fun setFavoriteCoin(
        coinListItem: CoinListItem,
        completeEvent: (Boolean) -> Unit
    ) {
        val userId = userProxy.getUser()?.id ?: completeEvent(false)
        Firebase.firestore.collection(USERS).document(userId.toString()).collection(FAVORITES)
            .document(coinListItem.id).set(coinListItem).addOnCompleteListener {
                completeEvent(it.isSuccessful)
            }
    }


    override suspend fun getFavoriteCoins(favoriteCoins: (List<CoinListItem>) -> Unit) {
        val userId = userProxy.getUser()?.id
        Firebase.firestore.collection(USERS).document(userId.toString())
            .collection(FAVORITES).get().addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    val listOfFavoriteCoins = result.result.documents.mapNotNull {
                        it.toDomain()
                    }
                    favoriteCoins(listOfFavoriteCoins)
                } else {
                    favoriteCoins(emptyList())
                }
            }
    }


    override suspend fun removeFavoriteCoin(id: String) {
        val userId = userProxy.getUser()?.id
        Firebase.firestore.collection(USERS).document(userId.toString()).collection(FAVORITES)
            .document(id)
            .delete()
    }


    override suspend fun isCoinFavorite(id: String, checkResult: (Boolean) -> Unit) {
        val userId = userProxy.getUser()?.id
        Firebase.firestore.collection(USERS).document(userId.toString()).collection(FAVORITES)
            .document(id)
            .get().addOnCompleteListener {
                if (it.result.data.isNullOrEmpty()) checkResult(false)
                else checkResult(true)
            }
    }

    override suspend fun logout() {
        Firebase.auth.signOut()
    }
}

