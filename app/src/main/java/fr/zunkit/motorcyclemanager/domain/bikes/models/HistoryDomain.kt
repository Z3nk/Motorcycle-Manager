package fr.zunkit.motorcyclemanager.domain.bikes.models


data class HistoryDomain(
    val id: Long,
    val date: String,
    val title: String,
    val description: String
)