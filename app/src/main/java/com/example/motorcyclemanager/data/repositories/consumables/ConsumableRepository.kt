package com.example.motorcyclemanager.data.repositories.consumables

import com.example.motorcyclemanager.data.doa.BikeDao
import com.example.motorcyclemanager.data.doa.ConsumableDao
import com.example.motorcyclemanager.data.models.BikeEntity
import com.example.motorcyclemanager.data.models.BikeWithConsumablesAndChecksEntity
import com.example.motorcyclemanager.data.models.CheckEntity
import com.example.motorcyclemanager.data.models.ConsumableEntity
import com.example.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConsumableRepository @Inject constructor(
    private val consumableDao: ConsumableDao
) {
    suspend fun createConsumable(
        consumable: ConsumableEntity
    ) {
        consumableDao.insertConsumable(consumable)
    }

    suspend fun addTimeTo(bikeId: Long, hoursToAdd: Float){
        consumableDao.addTimeToAllConsumables(bikeId, hoursToAdd)
    }

    suspend fun resetConsumable(consumableId: Long) {
        val consumable = consumableDao.getConsumableById(consumableId)
        consumable?.let {
            val resetConsumable = it.copy(currentTime = 0f)
            consumableDao.updateConsumable(resetConsumable)
        }
    }

}