package com.android.scankopi.di

import com.android.scankopi.data.repository.TestResultRepository
import com.android.scankopi.data.src.local.room.Database
import com.android.scankopi.data.src.remote.network.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTestResultRepository(
        testDatabase: Database
    ): TestResultRepository {
        return TestResultRepository(testDatabase, ApiConfig.getApiService())
    }
}