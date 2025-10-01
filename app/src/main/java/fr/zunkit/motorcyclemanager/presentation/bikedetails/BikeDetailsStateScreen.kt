package fr.zunkit.motorcyclemanager.presentation.bikedetails;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.zunkit.motorcyclemanager.design.theme.MotorcycleManagerTheme
import fr.zunkit.motorcyclemanager.presentation.bikedetails.composables.BikeSection
import fr.zunkit.motorcyclemanager.presentation.bikedetails.composables.ChecklistSection
import fr.zunkit.motorcyclemanager.presentation.bikedetails.composables.ConsumablesSection
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Bike
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Check
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Consumable

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
                BikeSection(
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