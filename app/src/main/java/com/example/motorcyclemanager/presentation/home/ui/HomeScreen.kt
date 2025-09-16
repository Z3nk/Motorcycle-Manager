package com.example.motorcyclemanager.presentation.home.ui

import android.widget.ProgressBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.motorcyclemanager.presentation.home.ui.screens.HomeStateScreen

sealed class HomeScreenUiState {

    data class HomeScreenState(
        val inputText: String = ""
    ) : HomeScreenUiState()

    object LoadingState : HomeScreenUiState()
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onNavigateToBikeDetail: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 2.dp
    ) {
        when (uiState) {
            is HomeScreenUiState.HomeScreenState -> HomeStateScreen(
                homeScreenState = uiState as HomeScreenUiState.HomeScreenState,
                onNavigateToBikeDetail = onNavigateToBikeDetail
            )

            HomeScreenUiState.LoadingState -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}