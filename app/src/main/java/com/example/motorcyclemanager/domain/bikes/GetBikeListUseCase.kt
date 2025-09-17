package com.example.motorcyclemanager.domain.bikes

import com.example.motorcyclemanager.data.repositories.bikes.BikeRepository
import com.example.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBikeListUseCase @Inject constructor(val bikeRepository: BikeRepository) {
    operator fun invoke(): Flow<List<BikeWithConsumablesAndChecksDomain>> {
        return bikeRepository.getAllBikes().map { bikeList -> bikeList.map { BikeWithConsumablesAndChecksDomain(it) } }
    }
}