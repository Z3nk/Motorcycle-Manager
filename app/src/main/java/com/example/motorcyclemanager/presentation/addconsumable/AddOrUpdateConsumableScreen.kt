package com.example.motorcyclemanager.presentation.addconsumable;

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
import com.example.motorcyclemanager.presentation.addconsumable.screens.AddOrUpdateConsumableStateScreen

sealed class AddConsumableScreenUiState {

    data class AddConsumableScreenState(
        val isLoading: Boolean,
        val name: String?,
        val time: Float?,
        val currentTime: Float?
    ) : AddConsumableScreenUiState()

    object LoadingState : AddConsumableScreenUiState()
}

@Composable
fun AddOrUpdateConsumableScreen(
    bikeId: Long,
    consumableId: Long?,
    viewModel: AddOrUpdateConsumableViewModel = hiltViewModel(),
    goBackToBikeDetail: () -> Unit

) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(bikeId, consumableId) {
        viewModel.initScreen(bikeId, consumableId)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 2.dp
    ) {
        when (uiState) {
            is AddConsumableScreenUiState.AddConsumableScreenState -> AddOrUpdateConsumableStateScreen(
                (uiState as AddConsumableScreenUiState.AddConsumableScreenState).name,
                (uiState as AddConsumableScreenUiState.AddConsumableScreenState).time,
                (uiState as AddConsumableScreenUiState.AddConsumableScreenState).currentTime,
                onNewConsumable = {
                    viewModel.onNewConsumable(it) {
                        goBackToBikeDetail()
                    }
                }
            )

            AddConsumableScreenUiState.LoadingState -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}