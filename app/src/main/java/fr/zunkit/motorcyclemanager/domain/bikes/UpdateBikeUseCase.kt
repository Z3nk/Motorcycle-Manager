package fr.zunkit.motorcyclemanager.domain.bikes

import fr.zunkit.motorcyclemanager.data.models.BikeEntity
import fr.zunkit.motorcyclemanager.data.models.CheckEntity
import fr.zunkit.motorcyclemanager.data.repositories.bikes.BikeRepository
import fr.zunkit.motorcyclemanager.data.repositories.checks.CheckRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.addcheck.models.AddOrUpdateCheck
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateBikeUseCase @Inject constructor(val bikeRepository: BikeRepository) {
    operator fun invoke(bikeId: Long, bikeName: String, bikeTime: Float): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                bikeRepository.updateBike(
                    BikeEntity(
                        id = bikeId,
                        name = bikeName,
                        time = bikeTime
                    )
                )
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }

    }
}