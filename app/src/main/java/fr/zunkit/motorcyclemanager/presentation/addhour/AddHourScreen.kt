package fr.zunkit.motorcyclemanager.presentation.addhour;

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
import fr.zunkit.motorcyclemanager.presentation.addhour.screens.AddHourStateScreen

sealed class AddHourScreenUiState {

    data class AddHourScreenState(
        val loading: Boolean,
        val currentBikeTime: Float
    ) : AddHourScreenUiState()

    object LoadingState : AddHourScreenUiState()
}

@Composable
fun AddHourScreen(
    bikeId: Long,
    viewModel: AddHourViewModel = hiltViewModel(),
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
            is AddHourScreenUiState.AddHourScreenState -> AddHourStateScreen(
                (uiState as AddHourScreenUiState.AddHourScreenState).currentBikeTime,
                onNewHour = { addHour ->
                    viewModel.onNewHour(addHour) {
                        goBackToBikeDetail()
                    }
                },
                onBackClick = goBackToBikeDetail

            )

            AddHourScreenUiState.LoadingState -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}