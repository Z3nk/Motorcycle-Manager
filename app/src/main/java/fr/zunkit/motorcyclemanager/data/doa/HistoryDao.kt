package fr.zunkit.motorcyclemanager.data.doa

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import fr.zunkit.motorcyclemanager.data.models.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert
    suspend fun insertHistory(history: HistoryEntity): Long

    @Insert
    suspend fun insertHistorys(historys: List<HistoryEntity>): List<Long>

    @Query("SELECT * FROM histories WHERE bikeId = :bikeId")
    fun getHistorysForBike(bikeId: Long): Flow<List<HistoryEntity>>

    @Update
    suspend fun updateHistory(history: HistoryEntity): Int

    @Query("SELECT * FROM histories WHERE id = :historyId")
    suspend fun getHistoryById(historyId: Long): HistoryEntity?

    @Delete
    suspend fun deleteHistory(history: HistoryEntity): Int

    @Query("DELETE FROM histories WHERE id = :historyId")
    suspend fun deleteHistoryById(historyId: Long): Int

    @Query("DELETE FROM histories WHERE bikeId = :bikeId")
    suspend fun deleteHistoryByBikeId(bikeId: Long)
}