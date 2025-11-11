package fr.zunkit.motorcyclemanager.domain.consumables

import fr.zunkit.motorcyclemanager.data.models.ConsumableEntity
import fr.zunkit.motorcyclemanager.data.models.HistoryEntity
import fr.zunkit.motorcyclemanager.data.repositories.consumables.ConsumableRepository
import fr.zunkit.motorcyclemanager.data.repositories.histories.HistoryRepository
import fr.zunkit.motorcyclemanager.models.Resource
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Consumable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

class RenewConsumableUseCase @Inject constructor(
    private val consumableRepository: ConsumableRepository,
    private val historyRepository: HistoryRepository
) {
    operator fun invoke(consumable: Consumable, bikeId: Long, note: String): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                consumableRepository.updateConsumable(
                    ConsumableEntity(
                        id = consumable.id,
                        bikeId = bikeId,
                        name = consumable.name,
                        time = consumable.time,
                        currentTime = 0.0f
                    )
                )
                historyRepository.createHistory(
                    HistoryEntity(
                        bikeId = bikeId,
                        title = consumable.name,
                        description = note,
                        time = consumable.currentTime,
                        date = getTodayAutoFormatted()
                    )
                )

                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }

    private fun getTodayAutoFormatted(): String {
        val locale = Locale.getDefault()
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
            .withLocale(locale)
        return LocalDateTime.now().format(formatter)
    }
}