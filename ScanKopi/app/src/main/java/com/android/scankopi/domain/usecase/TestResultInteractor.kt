package com.android.scankopi.domain.usecase

import com.android.scankopi.data.repository.TestResultRepository
import com.android.scankopi.data.src.local.entity.TestResultEntity
import javax.inject.Inject

class TestResultInteractor @Inject constructor(
    private val testResultRepository: TestResultRepository
): TestResultUseCase {
    override suspend fun getTestResultList(): List<TestResultEntity> {
        return testResultRepository.getTestResultList()
    }

    override suspend fun getLatestTestResultList(): List<TestResultEntity> {
        return testResultRepository.getLatestTestResultList()
    }

    override suspend fun getTestResultById(id: Int): TestResultEntity {
        return testResultRepository.getTestResultById(id)
    }

    override suspend fun addTestResult(data: TestResultEntity) {
        return testResultRepository.addTestResult(data)
    }

    override suspend fun deleteTestResult(id: Int) {
        return testResultRepository.deleteTestResult(id)
    }
}