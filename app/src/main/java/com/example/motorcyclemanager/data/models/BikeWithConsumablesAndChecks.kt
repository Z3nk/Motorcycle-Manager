package com.example.motorcyclemanager.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class BikeWithConsumablesAndChecks(
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
    val checks: List<CheckEntity>
)