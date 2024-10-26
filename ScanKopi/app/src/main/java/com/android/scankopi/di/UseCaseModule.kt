package com.android.scankopi.di

import com.android.scankopi.data.repository.TestResultRepository
import com.android.scankopi.domain.usecase.TestResultInteractor
import com.android.scankopi.domain.usecase.TestResultUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideTestResultUseCase(
        testResultRepository: TestResultRepository
    ): TestResultUseCase {
        return TestResultInteractor(testResultRepository)
    }
}