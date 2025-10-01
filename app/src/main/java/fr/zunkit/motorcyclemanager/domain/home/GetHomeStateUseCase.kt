package fr.zunkit.motorcyclemanager.domain.home

import fr.zunkit.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import fr.zunkit.motorcyclemanager.presentation.home.HomeScreenUiState
import fr.zunkit.motorcyclemanager.presentation.home.models.BikeWithConsumableAndChecks
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