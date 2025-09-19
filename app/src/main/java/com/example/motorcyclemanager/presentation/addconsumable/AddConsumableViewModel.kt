package com.example.motorcyclemanager.presentation.addconsumable;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorcyclemanager.presentation.addconsumable.models.AddConsumable
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
class AddConsumableViewModel @Inject constructor() : ViewModel() {
    private val loading = MutableStateFlow(false)

    val uiState: StateFlow<AddConsumableScreenUiState> = combine(
        loading,
        MutableStateFlow(Unit)
    ) { mLoading, i2 ->
        if (mLoading) {
            AddConsumableScreenUiState.LoadingState
        } else {
            AddConsumableScreenUiState.AddConsumableScreenState(mLoading)
        }

    }.flowOn(Dispatchers.Main).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AddConsumableScreenUiState.LoadingState
    )


    init {
        viewModelScope.launch(Dispatchers.Main) {

        }
    }

    fun onNewConsumable(addConsumable: AddConsumable){

    }
}