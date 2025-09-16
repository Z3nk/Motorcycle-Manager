package com.example.motorcyclemanager.presentation.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun HomeScreen(
    onNavigateToBikeDetail: () -> Unit,
) {
    Column {
        Text("Home Page")
        Button(onClick = { onNavigateToBikeDetail() }) {
            Text("Go to Bike")
        }
    }
}