package fr.zunkit.motorcyclemanager.presentation.bikedetails.models

import fr.zunkit.motorcyclemanager.domain.bikes.models.HistoryDomain


data class History(
    val id: Long,
    val date: String,
    val title: String,
    val description: String
) {
    constructor(entity: HistoryDomain) : this(
        id = entity.id,
        date = entity.date,
        title = entity.title,
        description = entity.description
    )
}