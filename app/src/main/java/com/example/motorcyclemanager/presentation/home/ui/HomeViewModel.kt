package com.example.motorcyclemanager.presentation.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorcyclemanager.data.models.CheckEntity
import com.example.motorcyclemanager.data.models.ConsumableEntity
import com.example.motorcyclemanager.data.repositories.BikeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val bikeRepository: BikeRepository) : ViewModel() {
    private val _inputText = MutableStateFlow("")
    private val _inputText2 = MutableStateFlow("")

    val uiState: StateFlow<HomeScreenUiState> = combine(
        _inputText,
        _inputText2
    ) { i1, i2 ->
        HomeScreenUiState.HomeScreenState(i1)
    }.flowOn(Dispatchers.Main).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        HomeScreenUiState.LoadingState
    )



    init {
        viewModelScope.launch(Dispatchers.Main) {
            _inputText.value = "1"
            bikeRepository.createBike(
                name = "Test Bike",
                consumables = listOf(ConsumableEntity(name = "Oil", time = 1000, bikeId = 0)),
                checks = listOf(CheckEntity(name = "Tire Check", done = false, bikeId = 0))
            )
        }
    }
}