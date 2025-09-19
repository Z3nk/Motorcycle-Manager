package com.example.motorcyclemanager.presentation.bikedetails.models


data class Check(
    val id: String,
    val name: String,
    val isCompleted: Boolean = false
)