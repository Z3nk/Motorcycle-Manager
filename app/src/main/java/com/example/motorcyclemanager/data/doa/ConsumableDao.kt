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
interface ConsumableDao {

    @Insert
    suspend fun insertConsumable(consumable: ConsumableEntity): Long

    @Insert
    suspend fun insertConsumables(consumables: List<ConsumableEntity>)

}