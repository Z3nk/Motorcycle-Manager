package fr.zunkit.motorcyclemanager.domain.checks

import fr.zunkit.motorcyclemanager.data.models.CheckEntity
import fr.zunkit.motorcyclemanager.data.repositories.checks.CheckRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Check
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