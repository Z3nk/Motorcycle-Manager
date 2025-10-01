package fr.zunkit.motorcyclemanager.domain.checks

import fr.zunkit.motorcyclemanager.data.models.CheckEntity
import fr.zunkit.motorcyclemanager.data.repositories.checks.CheckRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.addcheck.models.AddOrUpdateCheck
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddCheckUseCase @Inject constructor(val checkRepository: CheckRepository) {
    operator fun invoke(bikeId: Long, addOrUpdateCheck: AddOrUpdateCheck): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                checkRepository.createCheck(
                    CheckEntity(
                        bikeId = bikeId,
                        name = addOrUpdateCheck.name,
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