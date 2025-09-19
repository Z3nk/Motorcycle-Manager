package com.example.motorcyclemanager.presentation.bikedetails;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorcyclemanager.presentation.bikedetails.models.Bike
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BikeDetailsViewModel @Inject constructor() : ViewModel() {
    private val _inputText = MutableStateFlow("")
    private val _inputText2 = MutableStateFlow("")

    val uiState: StateFlow<BikeDetailsScreenUiState> = combine(
        _inputText,
        _inputText2
    ) { i1, i2 ->
        BikeDetailsScreenUiState.BikeDetailsScreenState(Bike("1", "name", 5.0f, listOf(), listOf()))
    }.flowOn(Dispatchers.Main).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        BikeDetailsScreenUiState.LoadingState
    )


    init {
        viewModelScope.launch(Dispatchers.Main) {

        }
    }
}