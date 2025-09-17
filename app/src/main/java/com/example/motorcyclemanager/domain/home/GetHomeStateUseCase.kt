package com.example.motorcyclemanager.domain.home

import com.example.motorcyclemanager.presentation.home.ui.HomeScreenUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetHomeStateUseCase @Inject constructor() {
    operator fun invoke(
        _inputText: MutableStateFlow<String>,
        _inputText2: MutableStateFlow<String>
    ): Flow<HomeScreenUiState> {
        return combine(_inputText, _inputText2) { flows ->
            val i1 = flows[0] as String
            val i2 = flows[1] as String

            HomeScreenUiState.HomeScreenState(i1)
        }
    }
}