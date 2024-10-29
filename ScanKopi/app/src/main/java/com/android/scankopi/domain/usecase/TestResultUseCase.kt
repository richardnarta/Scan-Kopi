package com.android.scankopi.domain.usecase

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import com.android.scankopi.data.src.local.entity.TestResultEntity
import com.android.scankopi.data.src.remote.response.Response
import com.android.scankopi.helper.Result

interface TestResultUseCase {
    suspend fun getTestResultList (): List<TestResultEntity>

    suspend fun getLatestTestResultList (): List<TestResultEntity>

    suspend fun getTestResultById (id: Int): TestResultEntity

    suspend fun addTestResult (data: TestResultEntity)

    suspend fun deleteTestResult (id: Int)

    suspend fun uploadToOnlineModel (context: Context, uri: Uri): LiveData<Result<Response>>
}