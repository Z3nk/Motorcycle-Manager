package com.example.motorcyclemanager.data.repositories.bikes

import com.example.motorcyclemanager.data.doa.BikeDao
import com.example.motorcyclemanager.data.models.BikeEntity
import com.example.motorcyclemanager.data.models.BikeWithConsumablesAndChecksEntity
import com.example.motorcyclemanager.data.models.CheckEntity
import com.example.motorcyclemanager.data.models.ConsumableEntity
import com.example.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BikeRepository @Inject constructor(
    private val bikeDao: BikeDao
) {
    // Example: Create a new bike with consumables and checks
    suspend fun createBike(
        name: String,
        time: Float,
        consumables: List<ConsumableEntity>,
        checks: List<CheckEntity>
    ) {
        val bike = BikeEntity(name = name, time = time)
        val bikeId = bikeDao.insertBike(bike)

        // Assign bikeId to consumables and checks
        val consumablesWithId = consumables.map { it.copy(bikeId = bikeId) }
        val checksWithId = checks.map { it.copy(bikeId = bikeId) }

        bikeDao.insertConsumables(consumablesWithId)
        bikeDao.insertChecks(checksWithId)
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