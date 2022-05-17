package com.aydinpolat.bitcointicker.di

import com.aydinpolat.bitcointicker.data.repository.FirebaseRepositoryImplementation
import com.aydinpolat.bitcointicker.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideRemoteDatabase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseRepository(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): FirebaseRepository {
        return FirebaseRepositoryImplementation(firebaseAuth, firestore)
    }
}