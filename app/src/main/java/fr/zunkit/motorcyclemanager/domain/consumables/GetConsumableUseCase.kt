package fr.zunkit.motorcyclemanager.domain.consumables;

import fr.zunkit.motorcyclemanager.data.repositories.consumables.ConsumableRepository
import fr.zunkit.motorcyclemanager.domain.bikes.models.CheckDomain
import fr.zunkit.motorcyclemanager.domain.bikes.models.ConsumableDomain
import fr.zunkit.motorcyclemanager.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetConsumableUseCase @Inject constructor(val consumableRepository: ConsumableRepository) {
    operator fun invoke(consumableId: Long): Flow<Resource<ConsumableDomain>> {
        return flow {
            try {
                emit(Resource.Loading())
                val consumableEntity = consumableRepository.getConsumableById(consumableId)
                consumableEntity?.let { lConsumable ->
                    emit(Resource.Success(ConsumableDomain(lConsumable.id, lConsumable.name, lConsumable.time, lConsumable.currentTime)))
                } ?: run {
                    emit(Resource.Error(Exception("No check found with this id")))
                }

            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }

    }
}