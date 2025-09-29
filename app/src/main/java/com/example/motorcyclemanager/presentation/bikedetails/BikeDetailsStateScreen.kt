package com.example.motorcyclemanager.presentation.bikedetails;

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.motorcyclemanager.R
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
    onDeleteConsumable: (Consumable) -> Unit,
    onAddCheck: () -> Unit,
    onEditCheck: (Check) -> Unit,
    onDeleteCheck: (Check) -> Unit,
    onClickCheck: (Check) -> Unit,
    onAddHours: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Retour",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
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
                    onAddHours = onAddHours
                )
            }

            item {
                ConsumablesSection(
                    consumables = screenState.bike.consumables,
                    onEditConsumable = onEditConsumable,
                    onAddConsumable = onAddConsumable,
                    onDeleteConsumable = onDeleteConsumable
                )
            }
            item {
                ChecklistSection(
                    checks = screenState.bike.checks,
                    onEditCheck = onEditCheck,
                    onDeleteCheck = onDeleteCheck,
                    onClickCheck = onClickCheck,
                    onAddCheck = onAddCheck
                )
            }
        }
    }
}

@Composable
private fun BikeHeader(
    bike: Bike,
    onAddHours: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = bike.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${bike.totalHours} h",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }

        Button(
            onClick = { onAddHours() },
            modifier = Modifier.clip(RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    12.dp,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_a_dirtbike)
                )
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.add_hours),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun ConsumablesSection(
    consumables: List<Consumable>,
    onEditConsumable: (Consumable) -> Unit,
    onDeleteConsumable: (Consumable) -> Unit,
    onAddConsumable: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
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
                    text = stringResource(R.string.consommables),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(onClick = onAddConsumable) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_consumable),
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
                            onEditClick = { onEditConsumable(consumable) },
                            onDeleteClick = { onDeleteConsumable(consumable) }
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
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
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
            .combinedClickable(
                onClick = onEditClick,
                onLongClick = { showMenu = true }),
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


            Box {
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    DropdownMenuItem(
                        text = { stringResource(R.string.update) },
                        onClick = {
                            showMenu = false
                            onEditClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { stringResource(R.string.delete) },
                        onClick = {
                            showMenu = false
                            onDeleteClick()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ChecklistSection(
    checks: List<Check>,
    onDeleteCheck: (Check) -> Unit,
    onEditCheck: (Check) -> Unit,
    onClickCheck: (Check) -> Unit,
    onAddCheck: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
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
                    text = stringResource(R.string.checklist),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(onClick = onAddCheck) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_check),
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
                            onClickCheck = { onClickCheck(check) },
                            onEditClick = { onEditCheck(check) },
                            onDeleteClick = { onDeleteCheck(check) }
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
    onClickCheck: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClickCheck,
                onLongClick = { showMenu = true }
            ),
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
                modifier = Modifier.size(24.dp),
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

            Box {
                IconButton(
                    onClick = { showMenu = true },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.more_options),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.update)) },
                        onClick = {
                            showMenu = false
                            onEditClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.delete)) },
                        onClick = {
                            showMenu = false
                            onDeleteClick()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyConsumablesState() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Timelapse,
            contentDescription = null,
            modifier = Modifier
                .padding(12.dp)
                .size(48.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(R.string.no_consumables),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(R.string.consumables_examples),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EmptyChecklistState() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Checklist,
            contentDescription = null,
            modifier = Modifier
                .padding(12.dp)
                .size(48.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(R.string.no_check_points),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(R.string.checks_examples),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Center
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
            onDeleteConsumable = {},
            onAddCheck = {},
            onEditCheck = {},
            onDeleteCheck = {},
            onAddHours = {},
            onClickCheck = {}
        )
    }
}