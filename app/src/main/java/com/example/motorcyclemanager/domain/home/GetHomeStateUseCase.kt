package com.example.motorcyclemanager.domain.home

import com.example.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import com.example.motorcyclemanager.presentation.home.ui.HomeScreenUiState
import com.example.motorcyclemanager.presentation.home.ui.models.BikeWithConsumableAndChecks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetHomeStateUseCase @Inject constructor() {
    operator fun invoke(
        bikeList: MutableStateFlow<List<BikeWithConsumablesAndChecksDomain>?>
    ): Flow<HomeScreenUiState> {
        return combine(bikeList) { flows ->
            val flowOfBikeWithConsumablesAndChecksDomainList = flows[0]

            val bikeList = flowOfBikeWithConsumablesAndChecksDomainList?.map { BikeWithConsumableAndChecks(it) }
            HomeScreenUiState.HomeScreenState(bikeList)
        }
    }
}