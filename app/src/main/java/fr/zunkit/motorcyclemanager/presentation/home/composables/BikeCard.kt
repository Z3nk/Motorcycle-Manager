package fr.zunkit.motorcyclemanager.presentation.home.composables

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.zunkit.motorcyclemanager.R
import fr.zunkit.motorcyclemanager.presentation.home.models.BikeWithConsumableAndChecks

@Composable
fun BikeCard(bike: BikeWithConsumableAndChecks, onClick: () -> Unit, onEditClick: () -> Unit, onDeleteCLick: () -> Unit) {

    var showMenu by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = { showMenu = true }),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row() {
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
            Box(modifier = Modifier.fillMaxHeight()) {
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                stringResource(R.string.update),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = {
                            showMenu = false
                            onEditClick()
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text(
                                stringResource(R.string.delete),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = {
                            showMenu = false
                            onDeleteCLick()
                        }
                    )
                }
            }
        }
    }

}