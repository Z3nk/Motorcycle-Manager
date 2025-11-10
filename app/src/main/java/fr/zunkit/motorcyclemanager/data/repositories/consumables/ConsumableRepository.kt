package fr.zunkit.motorcyclemanager.data.repositories.consumables

import fr.zunkit.motorcyclemanager.data.doa.ConsumableDao
import fr.zunkit.motorcyclemanager.data.doa.HistoryDao
import fr.zunkit.motorcyclemanager.data.models.CheckEntity
import fr.zunkit.motorcyclemanager.data.models.ConsumableEntity
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

    suspend fun updateConsumable(consumableEntity: ConsumableEntity) {
        consumableDao.updateConsumable(consumableEntity)
    }

    suspend fun deleteConsumableById(consumableId: Long) {
        consumableDao.deleteConsumableById(consumableId)
    }

}