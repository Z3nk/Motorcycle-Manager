package com.example.motorcyclemanager.data.repositories.checks

import com.example.motorcyclemanager.data.doa.BikeDao
import com.example.motorcyclemanager.data.doa.CheckDao
import com.example.motorcyclemanager.data.doa.ConsumableDao
import com.example.motorcyclemanager.data.models.BikeEntity
import com.example.motorcyclemanager.data.models.BikeWithConsumablesAndChecksEntity
import com.example.motorcyclemanager.data.models.CheckEntity
import com.example.motorcyclemanager.data.models.ConsumableEntity
import com.example.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckRepository @Inject constructor(
    private val checkDao: CheckDao
) {
    suspend fun createCheck(
        check: CheckEntity
    ) {
        checkDao.insertCheck(check)
    }

    suspend fun resetAllChecksForBike(bikeId: Long){
        checkDao.resetAllChecksForBike(bikeId)
    }

}