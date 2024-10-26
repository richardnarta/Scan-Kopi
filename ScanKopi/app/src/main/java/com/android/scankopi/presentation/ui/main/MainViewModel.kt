package com.android.scankopi.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.android.scankopi.domain.usecase.TestResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: TestResultUseCase
): ViewModel() {
    suspend fun testResults() = useCase.getLatestTestResultList()
}