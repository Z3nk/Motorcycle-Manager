package fr.zunkit.motorcyclemanager.presentation.bikedetails;

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import fr.zunkit.motorcyclemanager.presentation.bikedetails.models.Bike

sealed class BikeDetailsScreenUiState {

    data class BikeDetailsScreenState(
        val bike: Bike
    ) : BikeDetailsScreenUiState()

    object LoadingState : BikeDetailsScreenUiState()
}

@Composable
fun BikeDetailsScreen(
    bikeId: Long,
    viewModel: BikeDetailsViewModel = hiltViewModel(),
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToAddHourScreen: (idBike: Long) -> Unit,
    onNavigateToAddConsumableScreen: (idBike: Long) -> Unit,
    onNavigateToEditConsumableScreen: (idConsumable: Long) -> Unit,
    onNavigateToAddCheckScreen: (idBike: Long) -> Unit,
    onNavigateToEditCheckScreen: (idCheck: Long) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(bikeId) {
        viewModel.initScreen(bikeId)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 2.dp
    ) {
        when (uiState) {
            is BikeDetailsScreenUiState.BikeDetailsScreenState -> {
                val pickImageLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    uri?.let { viewModel.onPhotoPicked(it, bikeId) }
                }

                BikeDetailsStateScreen(
                    uiState as BikeDetailsScreenUiState.BikeDetailsScreenState,
                    onBackClick = onNavigateToHomeScreen,
                    onAddHours = {
                        onNavigateToAddHourScreen(bikeId)
                    },
                    onAddCheck = {
                        onNavigateToAddCheckScreen(bikeId)
                    },
                    onEditCheck = { check ->
                        onNavigateToEditCheckScreen(check.id)
                    },
                    onDeleteCheck = { check ->
                        viewModel.onDeleteCheck(check)
                    },
                    onAddConsumable = {
                        onNavigateToAddConsumableScreen(bikeId)
                    },
                    onEditConsumable = { consumable ->
                        onNavigateToEditConsumableScreen(consumable.id)
                    },
                    onDeleteConsumable = { consumable ->
                        viewModel.onDeleteConsumable(consumable)
                    },
                    onRenewConsumable = { consumable, note ->
                        viewModel.onRenewConsumable(consumable, note)

                    },
                    onClickCheck = { check ->
                        viewModel.checkOn(check)
                    },
                    onEditPhoto = {
                        pickImageLauncher.launch("image/*")
                    },
                    onAddInvoice = { uri ->
                        viewModel.onAddInvoice(uri, bikeId)
                    },
                    onDeleteInvoice = { invoice ->
                        viewModel.onDeleteInvoice(bikeId, invoice)
                    }
                )
            }

            BikeDetailsScreenUiState.LoadingState -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}