package fr.zunkit.motorcyclemanager.presentation.home.models

import fr.zunkit.motorcyclemanager.domain.bikes.models.BikeDomain
import fr.zunkit.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import fr.zunkit.motorcyclemanager.domain.bikes.models.CheckDomain
import fr.zunkit.motorcyclemanager.domain.bikes.models.ConsumableDomain

data class BikeWithConsumableAndChecks(
    val id: Long,
    val name: String,
    val time: Float,
    val consumablesSize: Int,
    val checksSize: Int
) {
    constructor(entity: BikeWithConsumablesAndChecksDomain) : this(
        id = entity.bike.id,
        name = entity.bike.name,
        time = entity.bike.time,
        consumablesSize = entity.consumables.size,
        checksSize = entity.checks.size
    )
}