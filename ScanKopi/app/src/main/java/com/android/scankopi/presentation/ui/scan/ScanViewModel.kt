package com.android.scankopi.presentation.ui.scan

import androidx.lifecycle.ViewModel
import com.android.scankopi.data.src.local.entity.TestResultEntity
import com.android.scankopi.domain.usecase.TestResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val useCase: TestResultUseCase
): ViewModel() {
    suspend fun addTestResult(data: TestResultEntity) = useCase.addTestResult(data)
}