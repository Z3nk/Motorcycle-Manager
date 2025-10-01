package fr.zunkit.motorcyclemanager.domain.bikes

import fr.zunkit.motorcyclemanager.data.repositories.bikes.BikeRepository
import fr.zunkit.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBikeListUseCase @Inject constructor(val bikeRepository: BikeRepository) {
    operator fun invoke(): Flow<List<BikeWithConsumablesAndChecksDomain>> {
        return bikeRepository.getAllBikes().map { bikeList -> bikeList.map { BikeWithConsumablesAndChecksDomain(it) } }
    }
}