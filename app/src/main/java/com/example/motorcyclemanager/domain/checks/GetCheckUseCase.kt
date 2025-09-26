package com.example.motorcyclemanager.domain.checks

import com.example.motorcyclemanager.data.repositories.checks.CheckRepository
import com.example.motorcyclemanager.domain.bikes.models.CheckDomain
import com.example.motorcyclemanager.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCheckUseCase @Inject constructor(val checkRepository: CheckRepository) {
    operator fun invoke(checkId: Long): Flow<Resource<CheckDomain>> {
        return flow {
            try {
                emit(Resource.Loading())
                val check = checkRepository.getCheck(checkId)
                check?.let { mCheck ->
                    emit(Resource.Success(CheckDomain(mCheck.id, mCheck.name, mCheck.done)))
                } ?: run {
                    emit(Resource.Error(Exception("No check found with this id")))
                }

            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }

    }
}