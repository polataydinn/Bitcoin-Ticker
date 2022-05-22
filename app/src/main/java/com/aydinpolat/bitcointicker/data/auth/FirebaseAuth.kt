package com.aydinpolat.bitcointicker.data.auth

import com.aydinpolat.bitcointicker.common.extentions.await
import com.aydinpolat.bitcointicker.domain.auth.Authenticator
import com.aydinpolat.bitcointicker.domain.auth.RegisterResult
import com.aydinpolat.bitcointicker.domain.auth.LoginResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuth : Authenticator {

    override suspend fun register(email: String, password: String): RegisterResult {
        return try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            RegisterResult.SUCCESS_REGISTER
        } catch (e: FirebaseAuthUserCollisionException) {
            RegisterResult.USER_ALREADY_EXISTS
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            RegisterResult.EMAIL_INVALID
        } catch (e: Exception) {
            RegisterResult.FAILURE_REGISTER
        }
    }

    override suspend fun login(email: String, password: String): LoginResult {
        return try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            LoginResult.SUCCESS_SIGN_IN
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            LoginResult.WRONG_PASSWORD
        } catch (e: FirebaseAuthInvalidUserException) {
            LoginResult.USER_NOT_FOUND
        } catch (e: Exception) {
            LoginResult.SIGN_IN_FAILED
        }
    }
}