package com.example.motorcyclemanager.domain.bikes.models

import com.example.motorcyclemanager.data.models.BikeWithConsumablesAndChecksEntity

data class BikeWithConsumablesAndChecks(
    val bike: Bike,
    val consumables: List<Consumable>,
    val checks: List<Check>
) {
    constructor(entity: BikeWithConsumablesAndChecksEntity) : this(
        bike = Bike(entity.bike.id, entity.bike.name),
        consumables = entity.consumables.map { Consumable(it.id, it.name, it.time) },
        checks = entity.checks.map { Check(it.id, it.name, it.done) }
    )
}