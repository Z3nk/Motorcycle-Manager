package fr.zunkit.motorcyclemanager.data.doa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import fr.zunkit.motorcyclemanager.data.models.BikeEntity
import fr.zunkit.motorcyclemanager.data.models.BikeWithConsumablesAndChecksEntity
import fr.zunkit.motorcyclemanager.data.models.CheckEntity
import fr.zunkit.motorcyclemanager.data.models.ConsumableEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BikeDao {

    @Insert
    suspend fun insertBike(bike: BikeEntity): Long

    @Update
    suspend fun updateBike(bike: BikeEntity): Int

    @Transaction
    @Query("SELECT * FROM bikes")
    fun getAllBikesWithDetails(): Flow<List<BikeWithConsumablesAndChecksEntity>>

    @Transaction
    @Query("SELECT * FROM bikes WHERE id = :bikeId")
    suspend fun getBikeWithDetails(bikeId: Long): BikeWithConsumablesAndChecksEntity?

}