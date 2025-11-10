package fr.zunkit.motorcyclemanager.data.repositories.histories

import fr.zunkit.motorcyclemanager.data.doa.HistoryDao
import fr.zunkit.motorcyclemanager.data.models.HistoryEntity
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val historyDao: HistoryDao
) {
    suspend fun getHistoryById(
        historyId: Long
    ): HistoryEntity? {
        return historyDao.getHistoryById(historyId)
    }

    suspend fun createHistory(
        history: HistoryEntity
    ) {
        historyDao.insertHistory(history)
    }

    suspend fun updateHistory(historyEntity: HistoryEntity) {
        historyDao.updateHistory(historyEntity)
    }

    suspend fun deleteHistoryById(historyId: Long) {
        historyDao.deleteHistoryById(historyId)
    }

}