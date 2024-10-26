package com.android.scankopi.domain.repository

import com.android.scankopi.data.src.local.entity.TestResultEntity

interface ITestResultRepository {
    suspend fun getTestResultList (): List<TestResultEntity>

    suspend fun getLatestTestResultList (): List<TestResultEntity>

    suspend fun getTestResultById (id: Int): TestResultEntity

    suspend fun addTestResult (data: TestResultEntity)

    suspend fun deleteTestResult (id: Int)
}