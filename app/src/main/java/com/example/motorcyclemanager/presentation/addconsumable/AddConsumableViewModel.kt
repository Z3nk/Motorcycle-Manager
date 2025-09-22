package com.example.motorcyclemanager.presentation.addconsumable;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorcyclemanager.domain.consumables.AddConsumableUseCase
import com.example.motorcyclemanager.models.Resource
import com.example.motorcyclemanager.presentation.addconsumable.models.AddConsumable
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
class AddConsumableViewModel @Inject constructor(private val addConsumableUseCase: AddConsumableUseCase) :
    ViewModel() {
    private val loading = MutableStateFlow(false)
    private val mBikeId = MutableStateFlow<Long?>(null)

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


    fun initScreen(bikeId: Long) {
        mBikeId.update { bikeId }
    }

    fun onNewConsumable(addConsumable: AddConsumable, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            loading.update { true }
            mBikeId.value?.let { bikeId ->
                addConsumableUseCase(bikeId, addConsumable).collectLatest { res ->
                    when (res) {
                        is Resource.Error<*> -> {
                            loading.update { false }
                        }

                        is Resource.Loading<*> -> {
                            loading.update { true }
                        }

                        is Resource.Success<*> -> {
                            loading.update { true }
                            onSuccess()
                        }
                    }

                }

            }
        }

    }
}