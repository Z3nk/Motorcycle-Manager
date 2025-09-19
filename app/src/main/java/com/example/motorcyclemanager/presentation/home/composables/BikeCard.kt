package com.example.motorcyclemanager.presentation.home.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motorcyclemanager.presentation.home.models.BikeWithConsumableAndChecks

@Composable
fun BikeCard(bike: BikeWithConsumableAndChecks, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = bike.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.TopStart)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Nombre de consommable : ${bike.consumablesSize}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Nombre de check : ${bike.checksSize}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Text(
                    text = "Nombre d'heure : ${bike.time}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Bottom)
                )
            }
        }
    }
}