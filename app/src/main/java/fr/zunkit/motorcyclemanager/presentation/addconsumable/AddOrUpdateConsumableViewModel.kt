package fr.zunkit.motorcyclemanager.presentation.addconsumable;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.zunkit.motorcyclemanager.domain.bikes.models.CheckDomain
import fr.zunkit.motorcyclemanager.domain.bikes.models.ConsumableDomain
import fr.zunkit.motorcyclemanager.domain.consumables.AddConsumableUseCase
import fr.zunkit.motorcyclemanager.domain.consumables.GetConsumableUseCase
import fr.zunkit.motorcyclemanager.domain.consumables.UpdateConsumableUseCase
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.addconsumable.models.AddOrUpdateConsumable
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
class AddOrUpdateConsumableViewModel @Inject constructor(
    private val addConsumableUseCase: AddConsumableUseCase,
    private val getConsumableUseCase: GetConsumableUseCase,
    private val updateConsumableUseCase: UpdateConsumableUseCase,
) :
    ViewModel() {
    private val mLoading = MutableStateFlow(false)
    private val mName = MutableStateFlow<String?>(null)
    private val mTime = MutableStateFlow<Float?>(null)
    private val mCurrentTime = MutableStateFlow<Float?>(null)
    private val mBikeId = MutableStateFlow<Long?>(null)
    private val mConsumableId = MutableStateFlow<Long?>(null)

    val uiState: StateFlow<AddConsumableScreenUiState> = combine(
        mLoading,
        mName,
        mTime,
        mCurrentTime
    ) { mLoading, name, time, currentTime ->
        if (mLoading) {
            AddConsumableScreenUiState.LoadingState
        } else {
            AddConsumableScreenUiState.AddConsumableScreenState(mLoading, name, time, currentTime)
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


    fun initScreen(bikeId: Long, consumableId: Long?) {
        mBikeId.update { bikeId }
        consumableId?.let { lconsumableId ->
            mConsumableId.update { lconsumableId }
            viewModelScope.launch(Dispatchers.Main) {
                getConsumableUseCase(consumableId = lconsumableId).collectLatest { res ->
                    when (res) {
                        is Resource.Error<*> -> {
                            mLoading.update { false }
                        }

                        is Resource.Loading<*> ->
                            mLoading.update { true }

                        is Resource.Success<ConsumableDomain> -> {
                            mName.update { res.data?.name }
                            mTime.update { res.data?.time }
                            mCurrentTime.update { res.data?.currentTime }
                            mLoading.update { false }
                        }
                    }
                }
            }
        }

    }

    fun onNewConsumable(addOrUpdateConsumable: AddOrUpdateConsumable, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            mLoading.update { true }
            mBikeId.value?.let { bikeId ->
                mConsumableId.value?.let { lConsumableId ->
                    updateConsumableUseCase(bikeId, consumableId = lConsumableId, addOrUpdateConsumable).collectLatest { res ->
                        when (res) {
                            is Resource.Error<*> -> {
                                mLoading.update { false }
                            }

                            is Resource.Loading<*> -> {
                                mLoading.update { true }
                            }

                            is Resource.Success<*> -> {
                                mLoading.update { true }
                                onSuccess()
                            }
                        }
                    }
                }?:run{
                    addConsumableUseCase(bikeId, addOrUpdateConsumable).collectLatest { res ->
                        when (res) {
                            is Resource.Error<*> -> {
                                mLoading.update { false }
                            }

                            is Resource.Loading<*> -> {
                                mLoading.update { true }
                            }

                            is Resource.Success<*> -> {
                                mLoading.update { true }
                                onSuccess()
                            }
                        }

                    }

                }

            }
        }

    }
}