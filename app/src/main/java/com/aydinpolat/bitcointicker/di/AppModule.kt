package com.aydinpolat.bitcointicker.di

import android.content.Context
import androidx.room.Room
import com.aydinpolat.bitcointicker.BuildConfig
import com.aydinpolat.bitcointicker.common.DataStoreManager
import com.aydinpolat.bitcointicker.data.local.CoinDao
import com.aydinpolat.bitcointicker.data.local.CoinDatabase
import com.aydinpolat.bitcointicker.data.remote.CoinApi
import com.aydinpolat.bitcointicker.data.repository.CoinRepositoryImplementation
import com.aydinpolat.bitcointicker.data.repository.FirebaseRepositoryImplementation
import com.aydinpolat.bitcointicker.domain.auth.UserProxy
import com.aydinpolat.bitcointicker.domain.repository.CoinRepository
import com.aydinpolat.bitcointicker.domain.repository.FirebaseRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRemoteDatabase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideCoinApi(): CoinApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(CoinApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinDao(coinDatabase: CoinDatabase): CoinDao {
        return coinDatabase.coinDao()
    }

    @Provides
    @Singleton
    fun provideCoinDatabase(@ApplicationContext appContext: Context): CoinDatabase {
        return Room.databaseBuilder(
            appContext,
            CoinDatabase::class.java,
            "Coins"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideFirebaseRepository(
        userProxy: UserProxy
    ): FirebaseRepository {
        return FirebaseRepositoryImplementation(userProxy)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(coinApi: CoinApi, coinDao: CoinDao): CoinRepository {
        return CoinRepositoryImplementation(coinApi, coinDao)
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}