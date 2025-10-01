package fr.zunkit.motorcyclemanager.domain.consumables

import fr.zunkit.motorcyclemanager.data.repositories.consumables.ConsumableRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Consumable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteConsumableUseCase @Inject constructor(private val consumableRepository: ConsumableRepository) {
    operator fun invoke(consumable: Consumable): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                consumableRepository.deleteConsumableById(consumable.id)
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}