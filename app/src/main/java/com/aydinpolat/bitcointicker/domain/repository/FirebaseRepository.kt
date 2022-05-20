package com.aydinpolat.bitcointicker.domain.repository

import com.aydinpolat.bitcointicker.data.remote.model.AuthenticationResult
import com.aydinpolat.bitcointicker.data.remote.model.UserDto
import com.aydinpolat.bitcointicker.domain.model.User

interface FirebaseRepository {
    suspend fun login(email: String, password: String, completeEvent: (AuthenticationResult) -> Unit)
    suspend fun signUp(userDto: UserDto, completeEvent: (AuthenticationResult) -> Unit)
    suspend fun saveUserToFirestore(user: User)
    suspend fun getUserName(completeEvent: (User) -> Unit)
    fun isUserLoggedIn(): Boolean
}