package com.android.scankopi.data.repository

import com.android.scankopi.data.src.local.entity.TestResultEntity
import com.android.scankopi.data.src.local.room.Database
import com.android.scankopi.domain.repository.ITestResultRepository
import javax.inject.Inject

class TestResultRepository @Inject constructor(
    private val testDatabase: Database
): ITestResultRepository {
    override suspend fun getTestResultList(): List<TestResultEntity> {
        return testDatabase.testResultDao().getAllTestResult()
    }

    override suspend fun getLatestTestResultList(): List<TestResultEntity> {
        return testDatabase.testResultDao().getLatestTestResult()
    }

    override suspend fun getTestResultById(id: Int): TestResultEntity {
        return testDatabase.testResultDao().getTestResultById(id)
    }

    override suspend fun addTestResult(data: TestResultEntity) {
        testDatabase.testResultDao().insertTestResult(data)
    }

    override suspend fun deleteTestResult(id: Int) {
        TODO("Not yet implemented")
    }
}