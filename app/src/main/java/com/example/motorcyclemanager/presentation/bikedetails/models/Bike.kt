package com.example.motorcyclemanager.presentation.bikedetails.models

import com.example.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain

data class Bike(
    val id: Long,
    val name: String,
    val totalHours: Float,
    val consumables: List<Consumable>,
    val checks: List<Check>
) {
    constructor(entity: BikeWithConsumablesAndChecksDomain) : this(
        id = entity.bike.id,
        name = entity.bike.name,
        totalHours = entity.bike.time,
        consumables = entity.consumables.map { Consumable(it) },
        checks = entity.checks.map { Check(it) })
}