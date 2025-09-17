package com.example.motorcyclemanager.presentation.home.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.motorcyclemanager.R
import com.example.motorcyclemanager.presentation.home.ui.HomeScreenUiState

@Composable
fun HomeStateScreen(
    homeScreenState: HomeScreenUiState.HomeScreenState,
    onNavigateToBikeDetail: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            stringResource(R.string.bike_list),
            style = MaterialTheme.typography.titleMedium
        )
        LazyColumn() {

        }
        Text("Home Page with state : ${homeScreenState.inputText}")
        Button(onClick = { onNavigateToBikeDetail() }) {
            Text("Go to Bike")
        }
    }
}