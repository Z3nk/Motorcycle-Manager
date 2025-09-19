package com.example.motorcyclemanager.presentation.bikedetails.models

import com.example.motorcyclemanager.domain.bikes.models.CheckDomain


data class Check(
    val id: Long,
    val name: String,
    val isCompleted: Boolean = false
) {
    constructor(entity: CheckDomain) : this(
        id = entity.id,
        name = entity.name,
        isCompleted = entity.done
    )
}