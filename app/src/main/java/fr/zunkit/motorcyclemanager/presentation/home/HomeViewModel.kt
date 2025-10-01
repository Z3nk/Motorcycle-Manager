package fr.zunkit.motorcyclemanager.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.zunkit.motorcyclemanager.domain.bikes.GetBikeListUseCase
import fr.zunkit.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import fr.zunkit.motorcyclemanager.domain.home.GetHomeStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeStateUseCase: GetHomeStateUseCase,
    private val getBikeListUseCase: GetBikeListUseCase
) :
    ViewModel() {
    private val bikeList = MutableStateFlow<List<BikeWithConsumablesAndChecksDomain>?>(null)

    val uiState: StateFlow<HomeScreenUiState> =
        getHomeStateUseCase(bikeList).flowOn(Dispatchers.Main).stateIn(
            viewModelScope,
            SharingStarted.Companion.Eagerly, HomeScreenUiState.LoadingState
        )


    init {
        viewModelScope.launch(Dispatchers.Main) {
            getBikeListUseCase().collectLatest { mBikeList ->
                bikeList.update { mBikeList }
            }
        }
    }
}