package fr.zunkit.motorcyclemanager.domain.bikes

import fr.zunkit.motorcyclemanager.data.repositories.bikes.BikeRepository
import fr.zunkit.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import fr.zunkit.motorcyclemanager.domain.bikes.models.CheckDomain
import fr.zunkit.motorcyclemanager.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBikeUseCase @Inject constructor(val bikeRepository: BikeRepository) {
    operator fun invoke(bikeId: Long): Flow<Resource<BikeWithConsumablesAndChecksDomain>> {
        return flow {
            try {
                emit(Resource.Loading())
                val bike = bikeRepository.getBikeById(bikeId)
                bike?.let { mBike ->
                    emit(Resource.Success(BikeWithConsumablesAndChecksDomain(mBike)))
                } ?: run {
                    emit(Resource.Error(Exception("No bike found with this id")))
                }

            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }

    }
}