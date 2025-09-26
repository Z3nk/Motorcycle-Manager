package com.example.motorcyclemanager.domain.consumables

import com.example.motorcyclemanager.data.models.ConsumableEntity
import com.example.motorcyclemanager.data.repositories.consumables.ConsumableRepository
import com.example.motorcyclemanager.models.Resource
import com.example.motorcyclemanager.presentation.addconsumable.models.AddConsumable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateConsumableUseCase @Inject constructor(private val consumableRepository: ConsumableRepository) {
    operator fun invoke(
        bikeId: Long,
        checkId: Long,
        addConsumable: AddConsumable
    ): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                consumableRepository.updateConsumable(
                    ConsumableEntity(
                        id = checkId,
                        bikeId = bikeId,
                        name = addConsumable.name,
                        time = addConsumable.time,
                        currentTime = addConsumable.currentTime
                    )
                )
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}