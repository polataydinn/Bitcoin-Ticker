package com.aydinpolat.bitcointicker.di

import com.aydinpolat.bitcointicker.data.auth.FirebaseAuth
import com.aydinpolat.bitcointicker.data.auth.FirebaseUser
import com.aydinpolat.bitcointicker.domain.auth.Authenticator
import com.aydinpolat.bitcointicker.domain.auth.UserProxy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthenticator(): Authenticator = FirebaseAuth()

    @Provides
    @Singleton
    fun provideUserProxy(): UserProxy = FirebaseUser()
}