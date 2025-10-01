package fr.zunkit.motorcyclemanager.domain.checks

import fr.zunkit.motorcyclemanager.data.models.CheckEntity
import fr.zunkit.motorcyclemanager.data.repositories.checks.CheckRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Check
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckCheckUseCase @Inject constructor(private val checkRepository: CheckRepository) {
    operator fun invoke(bikeId: Long, check: Check): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                checkRepository.updateCheck(CheckEntity(id = check.id, bikeId = bikeId ,name = check.name, done = !check.isCompleted))

                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}