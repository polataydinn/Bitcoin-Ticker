package com.aydinpolat.bitcointicker.data.repository

import com.aydinpolat.bitcointicker.data.model.AuthenticationResult
import com.aydinpolat.bitcointicker.data.model.User
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

    override fun isUserLoggedIn(): Boolean {
        firebaseAuth.currentUser?.let {
            return true
        } ?: kotlin.run { return false }
    }

    override suspend fun signUp(
        user: User,
        completeEvent: (AuthenticationResult) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                completeEvent(
                    AuthenticationResult(
                        it.isSuccessful,
                        it.exception?.localizedMessage ?: "You are successfully signed up."
                    )
                )
            }
    }

    override suspend fun saveUserToFirestore(
        user: User,
    ) {
        firestore.collection("users").document(user.email).set(user)
    }
}