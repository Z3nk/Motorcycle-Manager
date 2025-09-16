package com.example.motorcyclemanager.presentation.home.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.motorcyclemanager.presentation.home.ui.HomeScreenUiState

@Composable
fun HomeStateScreen (homeScreenState: HomeScreenUiState.HomeScreenState, onNavigateToBikeDetail: ()->Unit) {
    Column {
        Text("Home Page with state : ${homeScreenState.inputText}")
        Button(onClick = { onNavigateToBikeDetail() }) {
            Text("Go to Bike")
        }
    }
}