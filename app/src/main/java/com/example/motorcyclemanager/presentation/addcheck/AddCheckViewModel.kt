package com.example.motorcyclemanager.presentation.addcheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorcyclemanager.domain.bikes.checks.AddCheckUseCase
import com.example.motorcyclemanager.models.Resource
import com.example.motorcyclemanager.presentation.addcheck.models.AddCheck
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
class AddCheckViewModel @Inject constructor(private val addCheckUseCase: AddCheckUseCase) :
    ViewModel() {
    private val loading = MutableStateFlow(false)
    private val mBikeId = MutableStateFlow<Long?>(null)

    val uiState: StateFlow<AddCheckScreenUiState> = combine(
        loading,
        MutableStateFlow(Unit)
    ) { mLoading, i2 ->
        if (mLoading) {
            AddCheckScreenUiState.LoadingState
        } else {
            AddCheckScreenUiState.AddCheckScreenState(mLoading)
        }

    }.flowOn(Dispatchers.Main).stateIn(
        viewModelScope,
        SharingStarted.Companion.Eagerly,
        AddCheckScreenUiState.LoadingState
    )


    init {
        viewModelScope.launch(Dispatchers.Main) {

        }
    }


    fun initScreen(bikeId: Long) {
        mBikeId.update { bikeId }
    }

    fun onNewCheck(addCheck: AddCheck, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            loading.update { true }
            mBikeId.value?.let { bikeId ->
                addCheckUseCase(bikeId, addCheck).collectLatest { res ->
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