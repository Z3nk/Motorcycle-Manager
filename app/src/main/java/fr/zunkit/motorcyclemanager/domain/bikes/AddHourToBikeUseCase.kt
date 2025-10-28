package fr.zunkit.motorcyclemanager.domain.bikes;

import fr.zunkit.motorcyclemanager.data.repositories.bikes.BikeRepository
import fr.zunkit.motorcyclemanager.data.repositories.checks.CheckRepository
import fr.zunkit.motorcyclemanager.data.repositories.consumables.ConsumableRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.addhour.models.AddHour
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddHourToBikeUseCase @Inject constructor(
    val bikeRepository: BikeRepository,
    val checkRepository: CheckRepository,
    val consumableRepository: ConsumableRepository
) {
    operator fun invoke(
        bikeId: Long,
        addHour: AddHour
    ): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                var hoursToAdd = addHour.hour
                if(!addHour.isTimeRidingBike){
                    hoursToAdd = addHour.hour- (bikeRepository.getBikeById(bikeId)?.bike?.time?:0.0f)
                }

                bikeRepository.addHourToBike(
                    bikeId = bikeId,
                    hoursToAdd = hoursToAdd
                )

                consumableRepository.addTimeTo(
                    bikeId = bikeId,
                    hoursToAdd = hoursToAdd
                )

                if (addHour.resetChecks) {
                    checkRepository.resetAllChecksForBike(bikeId = bikeId)
                }

                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}