package com.example.motorcyclemanager.presentation.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun SettingsScreen(onNavigateToHomeScreen: () -> Unit) {

    Column {
        Text("SettingsScreen Page")
        Button(onClick = { onNavigateToHomeScreen() }) {
            Text("Go to Home")
        }
    }
}
