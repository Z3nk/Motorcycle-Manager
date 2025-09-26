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
interface CheckDao {


    @Transaction
    @Query("SELECT * FROM checks WHERE id = :checkId")
    suspend fun getCheck(checkId: Long): CheckEntity?

    @Insert
    suspend fun insertCheck(check: CheckEntity): Long

    @Insert
    suspend fun insertChecks(checks: List<CheckEntity>): List<Long>

    @Update
    suspend fun updateCheck(check: CheckEntity): Int

    @Delete
    suspend fun deleteCheck(check: CheckEntity): Int

    @Query("DELETE FROM checks WHERE id = :checkId")
    suspend fun deleteCheckById(checkId: Long): Int

    @Query("SELECT * FROM checks WHERE bikeId = :bikeId")
    fun getChecksForBike(bikeId: Long): Flow<List<CheckEntity>>

    @Query("UPDATE checks SET done = 0 WHERE bikeId = :bikeId")
    suspend fun resetAllChecksForBike(bikeId: Long): Int

    @Query("SELECT COUNT(*) FROM checks WHERE bikeId = :bikeId AND done = 0")
    suspend fun countPendingChecksForBike(bikeId: Long): Int
}