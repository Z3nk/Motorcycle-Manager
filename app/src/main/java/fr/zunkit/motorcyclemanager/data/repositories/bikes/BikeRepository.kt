package fr.zunkit.motorcyclemanager.data.repositories.bikes

import androidx.room.Transaction
import fr.zunkit.motorcyclemanager.data.doa.BikeDao
import fr.zunkit.motorcyclemanager.data.doa.CheckDao
import fr.zunkit.motorcyclemanager.data.doa.ConsumableDao
import fr.zunkit.motorcyclemanager.data.models.BikeEntity
import fr.zunkit.motorcyclemanager.data.models.BikeWithConsumablesAndChecksEntity
import fr.zunkit.motorcyclemanager.data.models.CheckEntity
import fr.zunkit.motorcyclemanager.data.models.ConsumableEntity
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

    suspend fun getBikeById(id: Long): BikeWithConsumablesAndChecksEntity? {
        return bikeDao.getBikeWithDetails(id)
    }

    suspend fun updateBike(bikeEntity: BikeEntity) {
        bikeDao.updateBike(bikeEntity)
    }

    @Transaction
    suspend fun deleteBikeById(bikeId: Long) {
        consumableDao.deleteConsumableByBikeId(bikeId)
        checkDao.deleteCheckByBikeId(bikeId)
        bikeDao.deleteBikeById(bikeId)
    }

}