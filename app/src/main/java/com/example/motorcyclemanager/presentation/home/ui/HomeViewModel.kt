package com.example.motorcyclemanager.presentation.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorcyclemanager.data.models.CheckEntity
import com.example.motorcyclemanager.data.models.ConsumableEntity
import com.example.motorcyclemanager.data.repositories.bikes.BikeRepository
import com.example.motorcyclemanager.domain.bikes.GetBikeListUseCase
import com.example.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import com.example.motorcyclemanager.domain.home.GetHomeStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeStateUseCase: GetHomeStateUseCase,
    private val getBikeListUseCase: GetBikeListUseCase
) :
    ViewModel() {
    private val bikeList = MutableStateFlow<List<BikeWithConsumablesAndChecksDomain>?>(null)

    val uiState: StateFlow<HomeScreenUiState> =
        getHomeStateUseCase(bikeList).flowOn(Dispatchers.Main).stateIn(
            viewModelScope,
            SharingStarted.Eagerly, HomeScreenUiState.LoadingState
        )


    init {
        viewModelScope.launch(Dispatchers.Main) {
            getBikeListUseCase().collectLatest { mBikeList ->
                bikeList.update { mBikeList }
            }
        }
    }
}