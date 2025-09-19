package com.example.motorcyclemanager.presentation.home.models

import com.example.motorcyclemanager.domain.bikes.models.BikeDomain
import com.example.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import com.example.motorcyclemanager.domain.bikes.models.CheckDomain
import com.example.motorcyclemanager.domain.bikes.models.ConsumableDomain

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