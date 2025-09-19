package com.example.motorcyclemanager.presentation.bikedetails;

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Motorcycle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SportsMotorsports
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.motorcyclemanager.design.theme.MotorcycleManagerTheme
import com.example.motorcyclemanager.presentation.bikedetails.models.Bike
import com.example.motorcyclemanager.presentation.bikedetails.models.Check
import com.example.motorcyclemanager.presentation.bikedetails.models.Consumable

@Composable
fun BikeDetailsStateScreen(
    screenState: BikeDetailsScreenUiState.BikeDetailsScreenState,
    onBackClick: () -> Unit,
    onAddConsumable: () -> Unit,
    onEditConsumable: (Consumable) -> Unit,
    onAddCheck: () -> Unit,
    onEditCheck: (Check) -> Unit,
    onAddHours: (Float) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        item {
            BikeHeader(
                bike = screenState.bike,
                onBackClick = onBackClick,
                onAddHours = onAddHours
            )
        }

        item {
            ConsumablesSection(
                consumables = screenState.bike.consumables,
                totalHours = screenState.bike.totalHours,
                onEditConsumable = onEditConsumable,
                onAddConsumable = onAddConsumable
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            ChecklistSection(
                checks = screenState.bike.checks,
                onEditCheck = onEditCheck,
                onAddCheck = onAddCheck
            )
        }
    }
}

@Composable
private fun BikeHeader(
    bike: Bike,
    onBackClick: () -> Unit,
    onAddHours: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Retour",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .background(
//                    color = MaterialTheme.colorScheme.surfaceVariant,
//                    shape = RoundedCornerShape(12.dp)
//                ),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Default.SportsMotorsports,
//                    contentDescription = "Moto",
//                    modifier = Modifier.size(64.dp),
//                    tint = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = "Image de la moto",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = bike.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${bike.totalHours} h",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }

            FloatingActionButton(
                onClick = { onAddHours(0f) },
                modifier = Modifier.size(48.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Ajouter des heures",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ConsumablesSection(
    consumables: List<Consumable>,
    totalHours: Float,
    onEditConsumable: (Consumable) -> Unit,
    onAddConsumable: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Consommables",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(onClick = onAddConsumable) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Ajouter consommable",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (consumables.isEmpty()) {
                EmptyConsumablesState()
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    consumables.forEach { consumable ->
                        ConsumableItem(
                            consumable = consumable,
                            onEditClick = { onEditConsumable(consumable) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ConsumableItem(
    consumable: Consumable,
    onEditClick: () -> Unit
) {
    val hoursRemaining = consumable.time - (consumable.currentTime ?: 0.0f)
    val progress = if (consumable.time > 0 && consumable.currentTime != null) {
        (consumable.currentTime / consumable.time).coerceIn(0f, 1f)
    } else {
        0f
    }
    val isUrgent = hoursRemaining <= 2f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEditClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isUrgent)
                MaterialTheme.colorScheme.errorContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = consumable.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${hoursRemaining}h restantes",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isUrgent)
                        MaterialTheme.colorScheme.onErrorContainer
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${consumable.time}h",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.End
                )

                Spacer(modifier = Modifier.height(4.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .width(60.dp)
                        .height(4.dp),
                    color = if (isUrgent)
                        MaterialTheme.colorScheme.error
                    else if (progress < 0.5f)
                        Color.Green
                    else
                        Color(0xFFFFA500), // Orange
                    trackColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                    gapSize = 0.dp,
                    drawStopIndicator = {}
                )
            }
        }
    }
}

@Composable
private fun ChecklistSection(
    checks: List<Check>,
    onEditCheck: (Check) -> Unit,
    onAddCheck: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Checklist",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(onClick = onAddCheck) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Ajouter check",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (checks.isEmpty()) {
                EmptyChecklistState()
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    checks.forEach { check ->
                        ChecklistItem(
                            check = check,
                            onEditClick = { onEditCheck(check) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChecklistItem(
    check: Check,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEditClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = check.isCompleted,
                onCheckedChange = null,
                modifier = Modifier.size(20.dp),
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = check.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onEditClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Modifier",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun EmptyConsumablesState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Inventory2,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Aucun consommable",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Ajoutez vos vidanges, pistons, etc.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun EmptyChecklistState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Aucune checklist",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Ajoutez vos vérifications pré-sortie",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun BikeDetailsPreview() {
    MotorcycleManagerTheme {
        BikeDetailsStateScreen(
            screenState = BikeDetailsScreenUiState.BikeDetailsScreenState(
                Bike(
                    id = 1,
                    name = "KTM 450 SX-F",
                    totalHours = 32f,
                    consumables = listOf(
                        Consumable(1, "Oil Change", 6.0f, 4.0f),
                        Consumable(2, "Piston", 50f, 28f),
                        Consumable(3, "Chaine", 20f, 5f),
                    ),
                    checks = listOf(
                        Check(1, "Grease chain", false),
                        Check(2, "WD40 on engine", false),
                        Check(3, "Blow bike", false),
                        Check(4, "Blow bike", false),
                    )
                )
            ),
            onBackClick = {},
            onAddConsumable = {},
            onEditConsumable = {},
            onAddCheck = {},
            onEditCheck = {},
            onAddHours = {}
        )
    }
}