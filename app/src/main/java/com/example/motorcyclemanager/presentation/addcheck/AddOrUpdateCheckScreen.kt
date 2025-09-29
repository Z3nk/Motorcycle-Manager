package com.example.motorcyclemanager.presentation.addcheck;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.motorcyclemanager.presentation.addcheck.screens.AddOrUpdateCheckStateScreen

sealed class AddCheckScreenUiState {

    data class AddCheckScreenState(
        val loading: Boolean,
        val checkName: String?
    ) : AddCheckScreenUiState()

    object LoadingState : AddCheckScreenUiState()
}

@Composable
fun AddOrUpdateCheckScreen(
    bikeId: Long,
    checkId: Long?,
    viewModel: AddOrUpdateCheckViewModel = hiltViewModel(),
    goBackToBikeDetail: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(bikeId, checkId) {
        viewModel.initScreen(bikeId, checkId)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 2.dp
    ) {
        when (uiState) {
            is AddCheckScreenUiState.AddCheckScreenState -> AddOrUpdateCheckStateScreen(
                checkName = (uiState as AddCheckScreenUiState.AddCheckScreenState).checkName,
                onNewCheck = {
                    viewModel.onNewCheck(it) {
                        goBackToBikeDetail()
                    }
                }

            )

            AddCheckScreenUiState.LoadingState -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}