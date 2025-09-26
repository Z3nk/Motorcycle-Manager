package com.example.motorcyclemanager.domain.consumables

import com.example.motorcyclemanager.data.models.ConsumableEntity
import com.example.motorcyclemanager.data.repositories.consumables.ConsumableRepository
import com.example.motorcyclemanager.models.Resource
import com.example.motorcyclemanager.presentation.addconsumable.models.AddOrUpdateConsumable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateConsumableUseCase @Inject constructor(private val consumableRepository: ConsumableRepository) {
    operator fun invoke(
        bikeId: Long,
        consumableId: Long,
        addOrUpdateConsumable: AddOrUpdateConsumable
    ): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                consumableRepository.updateConsumable(
                    ConsumableEntity(
                        id = consumableId,
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