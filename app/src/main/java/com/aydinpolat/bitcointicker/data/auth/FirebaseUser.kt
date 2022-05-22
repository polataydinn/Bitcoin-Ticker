package com.aydinpolat.bitcointicker.data.auth

import com.aydinpolat.bitcointicker.domain.auth.UserProxy
import com.aydinpolat.bitcointicker.domain.model.AuthUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseUser : UserProxy {

    override fun getUser(): AuthUser? {
        val firebaseUser = Firebase.auth.currentUser ?: return null
        return AuthUser(firebaseUser.uid, firebaseUser.email ?: "")
    }
}