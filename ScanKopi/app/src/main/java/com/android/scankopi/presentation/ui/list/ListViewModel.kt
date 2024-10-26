package com.android.scankopi.presentation.ui.list

import androidx.lifecycle.ViewModel
import com.android.scankopi.domain.usecase.TestResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val useCase: TestResultUseCase
): ViewModel(){
    suspend fun testResults() = useCase.getTestResultList()
}