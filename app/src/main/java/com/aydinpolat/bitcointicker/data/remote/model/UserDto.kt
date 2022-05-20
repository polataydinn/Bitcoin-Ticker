package com.aydinpolat.bitcointicker.data.remote.model

import com.aydinpolat.bitcointicker.domain.model.User
import com.google.firebase.firestore.DocumentSnapshot

data class UserDto(
    val email: String = "",
    val password: String = "",
    val fullName: String = ""
)

fun UserDto.toTrimmedUser(): User {
    return User(email = email, fullName = fullName)
}

fun DocumentSnapshot.toUserName(): User {
    val email = getString("email")
    val fullName = getString("fullName")
    return if (!fullName.isNullOrEmpty() && !email.isNullOrEmpty()) {
        User(email = email, fullName = fullName)
    } else {
        User()
    }
}