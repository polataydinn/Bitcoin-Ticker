package com.aydinpolat.bitcointicker.data.repository

import com.aydinpolat.bitcointicker.common.Constants.FAVORITES
import com.aydinpolat.bitcointicker.common.Constants.USERS
import com.aydinpolat.bitcointicker.data.remote.model.*
import com.aydinpolat.bitcointicker.domain.model.User
import com.aydinpolat.bitcointicker.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseRepositoryImplementation @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseRepository {

    override suspend fun login(
        email: String,
        password: String,
        completeEvent: (AuthenticationResult) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            completeEvent(
                AuthenticationResult(
                    it.isSuccessful,
                    it.exception?.localizedMessage ?: "You are successfully logged in."
                )
            )
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override fun isUserLoggedIn(): Boolean {
        firebaseAuth.currentUser?.let {
            return true
        } ?: run { return false }
    }

    override suspend fun signUp(
        userDto: UserDto,
        completeEvent: (AuthenticationResult) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(userDto.email, userDto.password)
            .addOnCompleteListener {
                completeEvent(
                    AuthenticationResult(
                        it.isSuccessful,
                        it.exception?.localizedMessage ?: "You are successfully signed up."
                    )
                )
            }
    }

    override suspend fun saveUserToFirestore(user: User) {
        firestore.collection(USERS).document(user.email).set(user)
    }

    override suspend fun getUserName(completeEvent: (User) -> Unit) {
        firebaseAuth.currentUser?.email?.let {
            firestore.collection(USERS).document(it).get()
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        completeEvent(result.result.toUserName())
                    }
                }
        }
    }

    override suspend fun setFavoriteCoin(
        coinListItem: CoinListItem,
        completeEvent: (Boolean) -> Unit
    ) {
        firebaseAuth.currentUser?.email?.let { email ->
            firestore.collection(USERS).document(email).collection(FAVORITES)
                .document(coinListItem.id).set(coinListItem).addOnCompleteListener {
                    completeEvent(it.isSuccessful)
                }
        }
    }

    override suspend fun getFavoriteCoins(favoriteCoins: (List<CoinListItem>) -> Unit) {
        firebaseAuth.currentUser?.email?.let { email ->
            firestore.collection(USERS).document(email)
                .collection(FAVORITES).get().addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        val listOfFavoriteCoins = result.result.documents.mapNotNull {
                            it.toCoinListItem()
                        }
                        favoriteCoins(listOfFavoriteCoins)
                    } else {
                        favoriteCoins(emptyList())
                    }
                }
        }
    }

    override suspend fun removeFavoriteIcon(id: String) {
        firebaseAuth.currentUser?.email?.let { email ->
            firestore.collection(USERS).document(email).collection(FAVORITES)
                .document(id)
                .delete()
        }
    }

    override suspend fun isCoinFavorite(id: String, checkResult: (Boolean) -> Unit) {
        firebaseAuth.currentUser?.email?.let { email ->
            firestore.collection(USERS).document(email).collection(FAVORITES).document(id)
                .get().addOnCompleteListener {
                    if (it.result.data.isNullOrEmpty()) checkResult(false)
                    else checkResult(true)
                }
        }
    }

}