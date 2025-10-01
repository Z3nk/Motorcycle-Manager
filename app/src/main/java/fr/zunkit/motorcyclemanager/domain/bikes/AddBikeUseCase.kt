package fr.zunkit.motorcyclemanager.domain.bikes;

import fr.zunkit.motorcyclemanager.data.repositories.bikes.BikeRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.addbike.models.AddOrUpdateBike
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddBikeUseCase @Inject constructor(val bikeRepository: BikeRepository) {
    operator fun invoke(addOrUpdateBike: AddOrUpdateBike): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                bikeRepository.createBike(
                    name = addOrUpdateBike.name,
                    time = addOrUpdateBike.time,
                    consumables = listOf(),
                    checks = listOf()
                )
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}