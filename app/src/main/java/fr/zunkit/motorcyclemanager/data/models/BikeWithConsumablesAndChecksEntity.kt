package fr.zunkit.motorcyclemanager.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class BikeWithConsumablesAndChecksEntity(
    @Embedded val bike: BikeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "bikeId"
    )
    val consumables: List<ConsumableEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "bikeId"
    )
    val checks: List<CheckEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "bikeId"
    )
    val histories: List<HistoryEntity>,
)