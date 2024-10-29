package com.android.scankopi.data.repository

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.android.scankopi.BuildConfig
import com.android.scankopi.data.src.local.entity.TestResultEntity
import com.android.scankopi.data.src.local.room.Database
import com.android.scankopi.data.src.remote.network.ApiService
import com.android.scankopi.data.src.remote.response.Response
import com.android.scankopi.domain.repository.ITestResultRepository
import com.android.scankopi.helper.GCP.getImageUrl
import com.android.scankopi.helper.Result
import javax.inject.Inject

class TestResultRepository @Inject constructor(
    private val testDatabase: Database,
    private val apiService: ApiService
): ITestResultRepository {
    private val testScanResult = MediatorLiveData<Result<Response>>()

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

    override suspend fun uploadToOnlineModel(context: Context, uri: Uri): LiveData<Result<Response>> {
        testScanResult.value = Result.Loading

        try {
            val imageUrl = getImageUrl(context, uri)

            if (imageUrl != null) {
                val response = apiService.postScanResult(BuildConfig.API_KEY, imageUrl.toString())

                if (response.code() == 200) {
                    val scanResultLiveData: LiveData<Response> = MutableLiveData(response.body())
                    testScanResult.addSource(scanResultLiveData) { data ->
                        testScanResult.value = Result.Success(data)
                    }
                } else {
                    testScanResult.value = Result.Error(response.message().toString())
                }
            }
        } catch (e: Exception) {
            testScanResult.value = Result.Error(e.toString())
        }

        return testScanResult
    }
}