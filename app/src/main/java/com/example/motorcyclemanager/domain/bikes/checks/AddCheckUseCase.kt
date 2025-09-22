package com.example.motorcyclemanager.domain.bikes.checks

import com.example.motorcyclemanager.data.models.CheckEntity
import com.example.motorcyclemanager.data.repositories.checks.CheckRepository
import com.example.motorcyclemanager.models.Resource
import com.example.motorcyclemanager.presentation.addcheck.models.AddCheck
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddCheckUseCase @Inject constructor(val checkRepository: CheckRepository) {
    operator fun invoke(bikeId: Long, addCheck: AddCheck): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                checkRepository.createCheck(
                    CheckEntity(
                        bikeId = bikeId,
                        name = addCheck.name,
                        done = false
                    )
                )
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }

    }
}