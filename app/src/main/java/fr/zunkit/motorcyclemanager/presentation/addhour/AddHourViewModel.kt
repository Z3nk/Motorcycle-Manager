package fr.zunkit.motorcyclemanager.presentation.addhour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.zunkit.motorcyclemanager.domain.bikes.AddHourToBikeUseCase
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.addhour.models.AddHour
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
class AddHourViewModel @Inject constructor(private val addHourUseCase: AddHourToBikeUseCase) :
    ViewModel() {
    private val loading = MutableStateFlow(false)
    private val mBikeId = MutableStateFlow<Long?>(null)

    val uiState: StateFlow<AddHourScreenUiState> = combine(
        loading,
        MutableStateFlow(Unit)
    ) { mLoading, i2 ->
        if (mLoading) {
            AddHourScreenUiState.LoadingState
        } else {
            AddHourScreenUiState.AddHourScreenState(mLoading)
        }

    }.flowOn(Dispatchers.Main).stateIn(
        viewModelScope,
        SharingStarted.Companion.Eagerly,
        AddHourScreenUiState.LoadingState
    )

    fun initScreen(bikeId: Long) {
        mBikeId.update { bikeId }
    }

    fun onNewHour(addHour: AddHour, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            loading.update { true }
            mBikeId.value?.let { bikeId ->
                addHourUseCase(
                    bikeId = bikeId,
                    addHour = addHour
                ).collectLatest { res ->
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