package com.example.motorcyclemanager.presentation.home

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
import com.example.motorcyclemanager.presentation.home.models.BikeWithConsumableAndChecks
import com.example.motorcyclemanager.presentation.home.screens.HomeStateScreen

sealed class HomeScreenUiState {

    data class HomeScreenState(
        val bikeList: List<BikeWithConsumableAndChecks>?
    ) : HomeScreenUiState()

    object LoadingState : HomeScreenUiState()
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
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