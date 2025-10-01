package fr.zunkit.motorcyclemanager.domain.bikes

import fr.zunkit.motorcyclemanager.data.repositories.bikes.BikeRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.home.models.BikeWithConsumableAndChecks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteBikeUseCase @Inject constructor(private val bikeRepository: BikeRepository) {
    operator fun invoke(bike: BikeWithConsumableAndChecks): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                bikeRepository.deleteBikeById(bike.id)

                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}