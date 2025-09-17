package com.example.motorcyclemanager.presentation.addbike;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorcyclemanager.presentation.addbike.models.BikeAdded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddBikeViewModel : ViewModel() {
    private val _inputText = MutableStateFlow("")
    private val _inputText2 = MutableStateFlow("")

    val uiState: StateFlow<AddBikeScreenUiState> = combine(
        _inputText,
        _inputText2
    ) { i1, i2 ->
        AddBikeScreenUiState.AddBikeScreenState(_inputText.value)
    }.flowOn(Dispatchers.Main).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AddBikeScreenUiState.LoadingState
    )


    init {
        viewModelScope.launch(Dispatchers.Main) {

        }
    }

    fun onBikeAdded(bikeAdded: BikeAdded){

    }
}