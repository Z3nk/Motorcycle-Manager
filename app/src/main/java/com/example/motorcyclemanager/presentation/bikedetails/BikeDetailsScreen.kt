package com.example.motorcyclemanager.presentation.bikedetails;

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
import com.example.motorcyclemanager.presentation.bikedetails.models.Bike

sealed class BikeDetailsScreenUiState {

    data class BikeDetailsScreenState(
        val bike: Bike
    ) : BikeDetailsScreenUiState()

    object LoadingState : BikeDetailsScreenUiState()
}

@Composable
fun BikeDetailsScreen(
    bikeId: Long,
    viewModel: BikeDetailsViewModel = hiltViewModel(),
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToAddConsumableScreen: (idBike: Long) -> Unit,
    onNavigateToAddCheckScreen: (idBike: Long) -> Unit
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
            is BikeDetailsScreenUiState.BikeDetailsScreenState -> {
                BikeDetailsStateScreen(
                    uiState as BikeDetailsScreenUiState.BikeDetailsScreenState,
                    onBackClick = onNavigateToHomeScreen,
                    onAddHours = {

                    },
                    onAddCheck = {
                        onNavigateToAddCheckScreen(bikeId)
                    },
                    onAddConsumable = {
                        onNavigateToAddConsumableScreen(bikeId)
                    },
                    onEditCheck = {

                    },
                    onEditConsumable = {}
                )
            }

            BikeDetailsScreenUiState.LoadingState -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}