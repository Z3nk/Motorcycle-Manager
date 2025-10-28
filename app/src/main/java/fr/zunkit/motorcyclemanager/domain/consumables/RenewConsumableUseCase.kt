package fr.zunkit.motorcyclemanager.domain.consumables

import fr.zunkit.motorcyclemanager.data.models.ConsumableEntity
import fr.zunkit.motorcyclemanager.data.repositories.consumables.ConsumableRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Consumable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RenewConsumableUseCase @Inject constructor(private val consumableRepository: ConsumableRepository) {
    operator fun invoke(consumable: Consumable, bikeId: Long): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                consumableRepository.updateConsumable(
                    ConsumableEntity(
                        id = consumable.id,
                        bikeId = bikeId,
                        name = consumable.name,
                        time = consumable.time,
                        currentTime = 0.0f
                    )
                )

                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}