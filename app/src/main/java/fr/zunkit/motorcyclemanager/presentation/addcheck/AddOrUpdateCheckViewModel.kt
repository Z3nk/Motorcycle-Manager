package fr.zunkit.motorcyclemanager.presentation.addcheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.zunkit.motorcyclemanager.domain.checks.AddCheckUseCase
import fr.zunkit.motorcyclemanager.domain.checks.GetCheckUseCase
import fr.zunkit.motorcyclemanager.domain.checks.UpdateCheckUseCase
import fr.zunkit.motorcyclemanager.domain.bikes.models.CheckDomain
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.addcheck.models.AddOrUpdateCheck
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
class AddOrUpdateCheckViewModel @Inject constructor(
    private val addCheckUseCase: AddCheckUseCase,
    private val getCheckUseCase: GetCheckUseCase,
    private val updateCheckUseCase: UpdateCheckUseCase
) :
    ViewModel() {
    private val mLoading = MutableStateFlow(false)
    private val mBikeId = MutableStateFlow<Long?>(null)
    private val mCheckId = MutableStateFlow<Long?>(null)
    private val mCheckName = MutableStateFlow<String?>(null)

    val uiState: StateFlow<AddCheckScreenUiState> = combine(
        mLoading,
        mCheckName
    ) { loading, checkName ->
        if (loading) {
            AddCheckScreenUiState.LoadingState
        } else {
            AddCheckScreenUiState.AddCheckScreenState(loading, checkName)
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


    fun initScreen(bikeId: Long, checkId: Long?) {
        mBikeId.update { bikeId }
        checkId?.let { lCheckId ->
            mCheckId.update { lCheckId }
            viewModelScope.launch(Dispatchers.Main) {
                getCheckUseCase(checkId = lCheckId).collectLatest { res ->
                    when (res) {
                        is Resource.Error<*> -> {
                            mLoading.update { false }
                        }

                        is Resource.Loading<*> ->
                            mLoading.update { true }

                        is Resource.Success<CheckDomain> -> {
                            mCheckName.update { res.data?.name }
                            mLoading.update { false }
                        }
                    }
                }
            }
        }
    }

    fun onNewCheck(addOrUpdateCheck: AddOrUpdateCheck, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            mLoading.update { true }
            mBikeId.value?.let { lBikeId ->
                mCheckId.value?.let { lCheckId ->
                    updateCheckUseCase(lBikeId, lCheckId, addOrUpdateCheck).collectLatest { res ->
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
                } ?: run {
                    addCheckUseCase(lBikeId, addOrUpdateCheck).collectLatest { res ->
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