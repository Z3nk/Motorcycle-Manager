package com.example.motorcyclemanager.data.repositories.consumables

import com.example.motorcyclemanager.data.doa.ConsumableDao
import com.example.motorcyclemanager.data.models.CheckEntity
import com.example.motorcyclemanager.data.models.ConsumableEntity
import javax.inject.Inject

class ConsumableRepository @Inject constructor(
    private val consumableDao: ConsumableDao
) {
    suspend fun getConsumableById(
        consumableId: Long
    ): ConsumableEntity? {
        return consumableDao.getConsumableById(consumableId)
    }

    suspend fun createConsumable(
        consumable: ConsumableEntity
    ) {
        consumableDao.insertConsumable(consumable)
    }

    suspend fun addTimeTo(bikeId: Long, hoursToAdd: Float) {
        consumableDao.addTimeToAllConsumables(bikeId, hoursToAdd)
    }

    suspend fun checkConsumable(consumableId: Long) {
        val consumable = consumableDao.getConsumableById(consumableId)
        consumable?.let {
            val resetConsumable = it.copy(currentTime = 0f)
            consumableDao.updateConsumable(resetConsumable)
        }
    }

    suspend fun updateConsumable(consumableEntity: ConsumableEntity) {
        consumableDao.updateConsumable(consumableEntity)
    }

    suspend fun deleteConsumableById(consumableId: Long) {
        consumableDao.deleteConsumableById(consumableId)
    }

}