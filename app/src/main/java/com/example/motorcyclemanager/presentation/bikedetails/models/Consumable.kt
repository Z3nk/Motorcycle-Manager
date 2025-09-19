package com.example.motorcyclemanager.presentation.bikedetails.models



data class Consumable(
    val id: String,
    val name: String,
    val time: Float,
    val currentTime: Float? = null
)