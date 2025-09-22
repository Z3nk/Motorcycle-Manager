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
        val loading: Boolean
    ) : AddBikeScreenUiState()

    object LoadingState : AddBikeScreenUiState()
}

@Composable
fun AddBikeScreen(
    viewModel: AddBikeViewModel = hiltViewModel(),
    onNavigateToHomeScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 2.dp
    ) {
        when (uiState) {
            is AddBikeScreenUiState.AddBikeScreenState -> {
                if ((uiState as AddBikeScreenUiState.AddBikeScreenState).loading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                } else {
                    AddBikeStateScreen {
                        viewModel.onBikeAdded(it) {
                            onNavigateToHomeScreen()
                        }
                    }
                }

            }

            AddBikeScreenUiState.LoadingState -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}