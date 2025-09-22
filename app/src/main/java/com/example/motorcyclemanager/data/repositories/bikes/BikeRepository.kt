package com.example.motorcyclemanager.data.repositories.bikes

import androidx.room.Transaction
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

class BikeRepository @Inject constructor(
    private val bikeDao: BikeDao,
    private val consumableDao: ConsumableDao,
    private val checkDao: CheckDao
) {
    suspend fun createBike(
        name: String,
        time: Float,
        consumables: List<ConsumableEntity>,
        checks: List<CheckEntity>
    ) {
        val bike = BikeEntity(name = name, time = time)
        val bikeId = bikeDao.insertBike(bike)

        val consumablesWithId = consumables.map { it.copy(bikeId = bikeId) }
        val checksWithId = checks.map { it.copy(bikeId = bikeId) }

        consumableDao.insertConsumables(consumablesWithId)
        checkDao.insertChecks(checksWithId)
    }

    @Transaction
    suspend fun addHourToBike(bikeId: Long, hoursToAdd: Float) {
        val currentBike = bikeDao.getBikeWithDetails(bikeId)
        if (currentBike != null) {
            val updatedBike = currentBike.bike.copy(time = currentBike.bike.time + hoursToAdd)
            bikeDao.updateBike(updatedBike)
        }
    }

    fun getAllBikes(): Flow<List<BikeWithConsumablesAndChecksEntity>> =
        bikeDao.getAllBikesWithDetails()

    suspend fun getBikeById(id: Long): BikeWithConsumablesAndChecksDomain? {
        val entity = bikeDao.getBikeWithDetails(id)
        return if (entity != null)
            BikeWithConsumablesAndChecksDomain(entity)
        else null
    }

}