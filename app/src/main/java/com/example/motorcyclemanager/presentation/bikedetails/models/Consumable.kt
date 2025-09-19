package com.example.motorcyclemanager.presentation.bikedetails.models

import com.example.motorcyclemanager.domain.bikes.models.BikeWithConsumablesAndChecksDomain
import com.example.motorcyclemanager.domain.bikes.models.ConsumableDomain
import com.example.motorcyclemanager.presentation.bikedetails.models.Bike


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