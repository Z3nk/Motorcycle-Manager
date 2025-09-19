package com.example.motorcyclemanager.presentation.bikedetails.models

data class Bike(
    val id: String,
    val name: String,
    val totalHours: Float,
    val consumables: List<Consumable>,
    val checks: List<Check>
)