package com.android.scankopi.presentation.ui.detail

import androidx.lifecycle.ViewModel
import com.android.scankopi.domain.usecase.TestResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: TestResultUseCase
): ViewModel() {
    suspend fun testResult (id: Int) = useCase.getTestResultById(id)
}