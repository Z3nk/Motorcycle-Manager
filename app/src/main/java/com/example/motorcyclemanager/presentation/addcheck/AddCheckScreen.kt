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
import com.example.motorcyclemanager.presentation.addcheck.screens.AddCheckStateScreen

sealed class AddCheckScreenUiState {

    data class AddCheckScreenState(
        val loading: Boolean
    ) : AddCheckScreenUiState()

    object LoadingState : AddCheckScreenUiState()
}

@Composable
fun AddCheckScreen(
    bikeId: Long,
    viewModel: AddCheckViewModel = hiltViewModel(),
    goBackToBikeDetail: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(bikeId) {
        viewModel.initScreen(bikeId)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 2.dp
    ) {
        when (uiState) {
            is AddCheckScreenUiState.AddCheckScreenState -> AddCheckStateScreen(
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