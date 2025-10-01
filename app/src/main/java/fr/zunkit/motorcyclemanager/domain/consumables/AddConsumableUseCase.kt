package fr.zunkit.motorcyclemanager.domain.consumables;

import fr.zunkit.motorcyclemanager.data.models.ConsumableEntity
import fr.zunkit.motorcyclemanager.data.repositories.consumables.ConsumableRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.addconsumable.models.AddOrUpdateConsumable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddConsumableUseCase @Inject constructor(val consumableRepository: ConsumableRepository) {
    operator fun invoke(bikeId: Long, addOrUpdateConsumable: AddOrUpdateConsumable): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                consumableRepository.createConsumable(
                    ConsumableEntity(
                        bikeId = bikeId,
                        name = addOrUpdateConsumable.name,
                        time = addOrUpdateConsumable.time,
                        currentTime = addOrUpdateConsumable.currentTime
                    )
                )
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}