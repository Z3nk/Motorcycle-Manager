package fr.zunkit.motorcyclemanager.data.repositories.checks

import fr.zunkit.motorcyclemanager.data.doa.BikeDao
import fr.zunkit.motorcyclemanager.data.doa.CheckDao
import fr.zunkit.motorcyclemanager.data.doa.ConsumableDao
import fr.zunkit.motorcyclemanager.data.models.BikeEntity
import fr.zunkit.motorcyclemanager.data.models.BikeWithConsumablesAndChecksEntity
import fr.zunkit.motorcyclemanager.data.models.CheckEntity
import fr.zunkit.motorcyclemanager.data.models.ConsumableEntity
import fr.zunkit.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckRepository @Inject constructor(
    private val checkDao: CheckDao
) {
    suspend fun getCheck(
        checkId: Long
    ): CheckEntity? {
        return checkDao.getCheck(checkId)
    }
    suspend fun createCheck(
        check: CheckEntity
    ) {
        checkDao.insertCheck(check)
    }

    suspend fun updateCheck(checkEntity: CheckEntity) {
        checkDao.updateCheck(checkEntity)
    }

    suspend fun deleteCheckById(checkId: Long) {
        checkDao.deleteCheckById(checkId = checkId)
    }


    suspend fun resetAllChecksForBike(bikeId: Long) {
        checkDao.resetAllChecksForBike(bikeId)
    }

}