package com.example.motorcyclemanager.presentation.bikedetails;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorcyclemanager.data.repositories.bikes.BikeRepository
import com.example.motorcyclemanager.domain.checks.CheckCheckUseCase
import com.example.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import com.example.motorcyclemanager.models.Resource
import com.example.motorcyclemanager.presentation.bikedetails.models.Bike
import com.example.motorcyclemanager.presentation.bikedetails.models.Check
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BikeDetailsViewModel @Inject constructor(
    private val bikeRepository: BikeRepository,
    private val checkCheckUseCase: CheckCheckUseCase
) :
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
        refreshBikeWith(bikeId)
    }

    fun checkOn(check: Check) {
        viewModelScope.launch(Dispatchers.Main) {
            bikeWithConsumablesAndChecksDomain.value?.bike?.id?.let { bikeId ->
                checkCheckUseCase(bikeId, check).collectLatest { res ->
                    when(res){
                        is Resource.Error<*> -> {}
                        is Resource.Loading<*> -> {}
                        is Resource.Success<*> -> {
                            refreshBikeWith(bikeId)
                        }
                    }
                }

            }
        }
    }

    private fun refreshBikeWith(bikeId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            bikeWithConsumablesAndChecksDomain.update { bikeRepository.getBikeById(bikeId) }
        }
    }
}