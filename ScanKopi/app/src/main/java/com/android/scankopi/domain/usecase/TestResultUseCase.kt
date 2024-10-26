package com.android.scankopi.domain.usecase

import com.android.scankopi.data.src.local.entity.TestResultEntity

interface TestResultUseCase {
    suspend fun getTestResultList (): List<TestResultEntity>

    suspend fun getLatestTestResultList (): List<TestResultEntity>

    suspend fun getTestResultById (id: Int): TestResultEntity

    suspend fun addTestResult (data: TestResultEntity)

    suspend fun deleteTestResult (id: Int)
}