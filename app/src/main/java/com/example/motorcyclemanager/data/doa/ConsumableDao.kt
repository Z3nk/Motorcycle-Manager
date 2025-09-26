package com.example.motorcyclemanager.data.doa

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.motorcyclemanager.data.models.BikeEntity
import com.example.motorcyclemanager.data.models.BikeWithConsumablesAndChecksEntity
import com.example.motorcyclemanager.data.models.CheckEntity
import com.example.motorcyclemanager.data.models.ConsumableEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsumableDao {

    @Insert
    suspend fun insertConsumable(consumable: ConsumableEntity): Long

    @Insert
    suspend fun insertConsumables(consumables: List<ConsumableEntity>): List<Long>

    @Query("SELECT * FROM consumables WHERE bikeId = :bikeId")
    fun getConsumablesForBike(bikeId: Long): Flow<List<ConsumableEntity>>

    @Query("UPDATE consumables SET currentTime = currentTime + :hoursToAdd WHERE bikeId = :bikeId")
    suspend fun addTimeToAllConsumables(bikeId: Long, hoursToAdd: Float): Int

    @Update
    suspend fun updateConsumable(consumable: ConsumableEntity): Int

    @Query("SELECT * FROM consumables WHERE id = :consumableId")
    suspend fun getConsumableById(consumableId: Long): ConsumableEntity?

    @Delete
    suspend fun deleteConsumable(consumable: ConsumableEntity): Int

    @Query("DELETE FROM consumables WHERE id = :consumableId")
    suspend fun deleteConsumableById(consumableId: Long): Int
}