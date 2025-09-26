package com.example.motorcyclemanager.domain.checks

import com.example.motorcyclemanager.data.models.CheckEntity
import com.example.motorcyclemanager.data.repositories.checks.CheckRepository
import com.example.motorcyclemanager.models.Resource
import com.example.motorcyclemanager.presentation.bikedetails.models.Check
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteCheckUseCase @Inject constructor(private val checkRepository: CheckRepository) {
    operator fun invoke(check: Check): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                checkRepository.deleteCheckById(check.id)

                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}