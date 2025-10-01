package fr.zunkit.motorcyclemanager.presentation.addbike;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.zunkit.motorcyclemanager.domain.bikes.AddBikeUseCase
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.addbike.models.AddOrUpdateBike
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.zunkit.motorcyclemanager.data.repositories.bikes.BikeRepository
import fr.zunkit.motorcyclemanager.domain.bikes.GetBikeUseCase
import fr.zunkit.motorcyclemanager.domain.bikes.UpdateBikeUseCase
import fr.zunkit.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
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
class AddorUpdateBikeViewModel @Inject constructor(
    private val addBikeUseCase: AddBikeUseCase,
    private val updateBikeUseCase: UpdateBikeUseCase,
    private val getBikeUseCase: GetBikeUseCase
) :
    ViewModel() {
    private val bikeWithConsumablesAndChecksDomain =
        MutableStateFlow<BikeWithConsumablesAndChecksDomain?>(null)
    private val loading = MutableStateFlow(false)

    val uiState: StateFlow<AddBikeScreenUiState> = combine(
        loading,
        bikeWithConsumablesAndChecksDomain
    ) { mLoading, mBikeWithConsumablesAndChecksDomain ->
        AddBikeScreenUiState.AddBikeScreenState(
            loading = mLoading,
            bikeName = mBikeWithConsumablesAndChecksDomain?.bike?.name,
            bikeTime = mBikeWithConsumablesAndChecksDomain?.bike?.time
        )
    }.flowOn(Dispatchers.Main).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AddBikeScreenUiState.LoadingState
    )

    fun onBikeAdded(addOrUpdateBike: AddOrUpdateBike, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            bikeWithConsumablesAndChecksDomain.value?.let { lbikeWithConsumablesAndChecksDomain ->
                updateBikeUseCase(
                    lbikeWithConsumablesAndChecksDomain.bike.id,
                    addOrUpdateBike.name,
                    addOrUpdateBike.time
                ).collectLatest {
                    when (it) {
                        is Resource.Error<*> -> loading.update { false }
                        is Resource.Loading<*> -> loading.update { true }
                        is Resource.Success<*> -> {
                            loading.update { false }
                            onSuccess()
                        }
                    }
                }
            } ?: run {
                addBikeUseCase(addOrUpdateBike).collectLatest {
                    when (it) {
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

    fun initScreen(bikeId: Long?) {
        viewModelScope.launch(Dispatchers.Main) {
            bikeId?.let { lBikeId ->
                getBikeUseCase(lBikeId).collectLatest { res ->
                    when (res) {
                        is Resource.Error<*> -> loading.update { false }
                        is Resource.Loading<*> -> loading.update { true }
                        is Resource.Success<*> -> {
                            bikeWithConsumablesAndChecksDomain.update { res.data }
                            loading.update { false }
                        }
                    }
                }

            }

        }
    }
}