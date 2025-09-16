package com.example.motorcyclemanager.presentation.bikedetails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun BikeDetailsScreen(onNavigateToHomeScreen: () -> Unit) {

    Column {
        Text("Bike Page")
        Button(onClick = { onNavigateToHomeScreen() }) {
            Text("Go to Home")
        }
    }
}