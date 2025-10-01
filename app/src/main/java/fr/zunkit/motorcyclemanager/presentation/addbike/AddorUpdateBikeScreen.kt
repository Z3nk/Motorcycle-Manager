package fr.zunkit.motorcyclemanager.presentation.addbike;

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
import fr.zunkit.motorcyclemanager.presentation.addbike.screens.AddBikeStateScreen


sealed class AddBikeScreenUiState {

    data class AddBikeScreenState(
        val loading: Boolean,
        val bikeName: String?,
        val bikeTime: Float?
    ) : AddBikeScreenUiState()

    object LoadingState : AddBikeScreenUiState()
}

@Composable
fun AddBikeScreen(
    bikeId: Long?,
    viewModel: AddorUpdateBikeViewModel = hiltViewModel(),
    onNavigateToHomeScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(bikeId, ) {
        viewModel.initScreen(bikeId)
    }
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
                    AddBikeStateScreen(
                        (uiState as AddBikeScreenUiState.AddBikeScreenState).bikeName,
                        (uiState as AddBikeScreenUiState.AddBikeScreenState).bikeTime,
                        onNewBike = {
                            viewModel.onBikeAdded(it) {
                                onNavigateToHomeScreen()
                            }
                        },
                        onBackClick = onNavigateToHomeScreen
                    )
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