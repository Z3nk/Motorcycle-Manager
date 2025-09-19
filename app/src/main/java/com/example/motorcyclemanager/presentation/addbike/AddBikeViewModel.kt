package com.example.motorcyclemanager.presentation.addbike;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorcyclemanager.domain.bikes.AddBikeUseCase
import com.example.motorcyclemanager.models.Resource
import com.example.motorcyclemanager.presentation.addbike.models.BikeAdded
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
class AddBikeViewModel @Inject constructor(private val addBikeUseCase: AddBikeUseCase) :
    ViewModel() {
    private val loading = MutableStateFlow(false)

    val uiState: StateFlow<AddBikeScreenUiState> = combine(
        loading,
        MutableStateFlow(Unit)
    ) { mLoading, _ ->
        AddBikeScreenUiState.AddBikeScreenState(loading = mLoading)
    }.flowOn(Dispatchers.Main).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AddBikeScreenUiState.LoadingState
    )

    fun onBikeAdded(bikeAdded: BikeAdded, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            addBikeUseCase(bikeAdded).collectLatest {
                when(it){
                    is Resource.Error<*> -> loading.update { false }
                    is Resource.Loading<*> -> loading.update { true }
                    is Resource.Success<*> -> {
                        loading.update { false }
                        onSuccess()
                    }
                }
            }
        }
    }
}