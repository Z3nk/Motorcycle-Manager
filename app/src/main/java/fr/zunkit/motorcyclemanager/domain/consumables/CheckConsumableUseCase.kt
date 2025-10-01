package fr.zunkit.motorcyclemanager.domain.consumables

import fr.zunkit.motorcyclemanager.data.repositories.consumables.ConsumableRepository
import fr.zunkit.motorcyclemanager.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckConsumableUseCase @Inject constructor(private val consumableRepository: ConsumableRepository) {
    operator fun invoke(consumableId: Long): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                consumableRepository.checkConsumable(consumableId)
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}