package fr.zunkit.motorcyclemanager.domain.bikes.models

import fr.zunkit.motorcyclemanager.data.models.BikeWithConsumablesAndChecksEntity
import kotlin.math.roundToInt

data class BikeWithConsumablesAndChecksDomain(
    val bike: BikeDomain,
    val consumables: List<ConsumableDomain>,
    val checks: List<CheckDomain>
) {
    constructor(entity: BikeWithConsumablesAndChecksEntity) : this(
        bike = BikeDomain(
            entity.bike.id,
            entity.bike.name,
            (entity.bike.time * 10.0f).roundToInt() / 10f
        ),
        consumables = entity.consumables.map {
            ConsumableDomain(
                it.id,
                it.name,
                it.time,
                (it.currentTime * 10.0f).roundToInt() / 10f
            )
        },
        checks = entity.checks.map { CheckDomain(it.id, it.name, it.done) }
    )
}