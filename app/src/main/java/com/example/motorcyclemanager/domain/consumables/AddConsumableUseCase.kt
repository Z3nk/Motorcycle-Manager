package com.example.motorcyclemanager.domain.consumables;

import com.example.motorcyclemanager.data.models.ConsumableEntity
import com.example.motorcyclemanager.data.repositories.consumables.ConsumableRepository
import com.example.motorcyclemanager.models.Resource
import com.example.motorcyclemanager.presentation.addconsumable.models.AddConsumable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddConsumableUseCase @Inject constructor(val consumableRepository: ConsumableRepository) {
    operator fun invoke(bikeId: Long, addConsumable: AddConsumable): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                consumableRepository.createConsumable(
                    ConsumableEntity(
                        bikeId = bikeId,
                        name = addConsumable.name,
                        time = addConsumable.time,
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