package fr.zunkit.motorcyclemanager.domain.bikes.models

data class CheckDomain(
    val id: Long,
    val name: String,
    val done: Boolean
)