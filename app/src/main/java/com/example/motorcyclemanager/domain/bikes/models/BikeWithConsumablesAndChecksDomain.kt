package com.example.motorcyclemanager.domain.bikes.models

import com.example.motorcyclemanager.data.models.BikeWithConsumablesAndChecksEntity

data class BikeWithConsumablesAndChecksDomain(
    val bike: BikeDomain,
    val consumables: List<ConsumableDomain>,
    val checks: List<CheckDomain>
) {
    constructor(entity: BikeWithConsumablesAndChecksEntity) : this(
        bike = BikeDomain(entity.bike.id, entity.bike.name, entity.bike.time),
        consumables = entity.consumables.map { ConsumableDomain(it.id, it.name, it.time) },
        checks = entity.checks.map { CheckDomain(it.id, it.name, it.done) }
    )
}