package fr.zunkit.motorcyclemanager.presentation.home

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
import fr.zunkit.motorcyclemanager.presentation.home.models.BikeWithConsumableAndChecks
import fr.zunkit.motorcyclemanager.presentation.home.screens.HomeStateScreen

sealed class HomeScreenUiState {

    data class HomeScreenState(
        val bikeList: List<BikeWithConsumableAndChecks>?
    ) : HomeScreenUiState()

    object LoadingState : HomeScreenUiState()
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToBikeDetail: (Long) -> Unit,
    onNavigateToAddBike: () -> Unit,
    onNavigateToEditBike: (BikeWithConsumableAndChecks) -> Unit,
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
                onNavigateToBikeDetail = onNavigateToBikeDetail,
                onNavigateToAddBike = onNavigateToAddBike,
                onNavigateToEditBike = onNavigateToEditBike,
                onDeleteBike = { bike -> viewModel.onDeleteBike(bike) }
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