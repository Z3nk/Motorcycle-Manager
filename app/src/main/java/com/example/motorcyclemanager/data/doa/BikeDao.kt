package com.example.motorcyclemanager.data.doa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.motorcyclemanager.data.models.BikeEntity
import com.example.motorcyclemanager.data.models.BikeWithConsumablesAndChecksEntity
import com.example.motorcyclemanager.data.models.CheckEntity
import com.example.motorcyclemanager.data.models.ConsumableEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BikeDao {

    @Insert
    suspend fun insertBike(bike: BikeEntity): Long

    @Insert
    suspend fun insertConsumables(consumables: List<ConsumableEntity>)

    @Insert
    suspend fun insertChecks(checks: List<CheckEntity>)

    @Transaction
    @Query("SELECT * FROM bikes")
    fun getAllBikesWithDetails(): Flow<List<BikeWithConsumablesAndChecksEntity>>

    @Transaction
    @Query("SELECT * FROM bikes WHERE id = :bikeId")
    suspend fun getBikeWithDetails(bikeId: Long): BikeWithConsumablesAndChecksEntity?

}