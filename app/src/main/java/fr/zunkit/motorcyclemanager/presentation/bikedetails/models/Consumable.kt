package fr.zunkit.motorcyclemanager.presentation.bikedetails.models

import fr.zunkit.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import fr.zunkit.motorcyclemanager.domain.bikes.models.ConsumableDomain
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Bike


data class Consumable(
    val id: Long,
    val name: String,
    val time: Float,
    val currentTime: Float? = null
){
    constructor(entity: ConsumableDomain) : this(
        id = entity.id,
        name = entity.name,
        time = entity.time,
        currentTime = entity.currentTime)
}