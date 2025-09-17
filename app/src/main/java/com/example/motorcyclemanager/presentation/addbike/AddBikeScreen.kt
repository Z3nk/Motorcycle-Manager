package com.example.motorcyclemanager.presentation.addbike;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.motorcyclemanager.presentation.addbike.screens.AddBikeStateScreen


sealed class AddBikeScreenUiState {

    data class AddBikeScreenState(
        val text: String
    ) : AddBikeScreenUiState()

    object LoadingState : AddBikeScreenUiState()
}

@Composable
fun AddBikeScreen(
    viewModel: AddBikeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 2.dp
    ) {
        when (uiState) {
            is AddBikeScreenUiState.AddBikeScreenState -> AddBikeStateScreen(
                uiState as AddBikeScreenUiState.AddBikeScreenState
            )

            AddBikeScreenUiState.LoadingState -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}