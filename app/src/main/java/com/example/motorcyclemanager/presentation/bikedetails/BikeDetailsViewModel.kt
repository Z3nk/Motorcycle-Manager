package com.example.motorcyclemanager.presentation.bikedetails;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorcyclemanager.data.repositories.bikes.BikeRepository
import com.example.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import com.example.motorcyclemanager.presentation.bikedetails.models.Bike
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BikeDetailsViewModel @Inject constructor(private val bikeRepository: BikeRepository) :
    ViewModel() {
    private val bikeWithConsumablesAndChecksDomain =
        MutableStateFlow<BikeWithConsumablesAndChecksDomain?>(null)

    private val loading = MutableStateFlow(false)

    val uiState: StateFlow<BikeDetailsScreenUiState> = combine(
        bikeWithConsumablesAndChecksDomain,
        loading
    ) { _bikeWithConsumablesAndChecksDomain, _loading ->
        if (_loading || _bikeWithConsumablesAndChecksDomain == null) {
            BikeDetailsScreenUiState.LoadingState
        } else {
            BikeDetailsScreenUiState.BikeDetailsScreenState(
                Bike(_bikeWithConsumablesAndChecksDomain)
            )
        }
    }.flowOn(Dispatchers.Main).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        BikeDetailsScreenUiState.LoadingState
    )

    fun initScreen(bikeId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            bikeWithConsumablesAndChecksDomain.update { bikeRepository.getBikeById(bikeId) }
        }
    }
}