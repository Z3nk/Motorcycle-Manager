package fr.zunkit.motorcyclemanager.presentation.home.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.zunkit.motorcyclemanager.R
import fr.zunkit.motorcyclemanager.design.theme.MotorcycleManagerTheme
import fr.zunkit.motorcyclemanager.presentation.home.HomeScreenUiState
import fr.zunkit.motorcyclemanager.presentation.home.models.BikeWithConsumableAndChecks
import fr.zunkit.motorcyclemanager.presentation.home.screens.tabs.HomeTab
import fr.zunkit.motorcyclemanager.presentation.home.screens.tabs.SettingsTab


@Composable
fun HomeStateScreen(
    homeScreenState: HomeScreenUiState.HomeScreenState,
    onNavigateToBikeDetail: (Long) -> Unit,
    onNavigateToAddBike: () -> Unit,
    onNavigateToEditBike: (BikeWithConsumableAndChecks) -> Unit,
    onDeleteBike: (BikeWithConsumableAndChecks) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text(stringResource(R.string.home), fontWeight = FontWeight.Bold) },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    label = {
                        Text(
                            stringResource(R.string.about),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    alwaysShowLabel = true
                )
            }
        }
    ) { paddingValues ->
        if (selectedTab == 0) {
            HomeTab(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                onNavigateToAddBike = onNavigateToAddBike,
                homeScreenState = homeScreenState,
                onNavigateToBikeDetail = onNavigateToBikeDetail,
                onNavigateToEditBike = onNavigateToEditBike,
                onDeleteBike = onDeleteBike
            )
        } else {
            SettingsTab(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MotorcycleManagerTheme {
        HomeStateScreen(
            homeScreenState = HomeScreenUiState.HomeScreenState(listOf()),
            onDeleteBike = { _ -> },
            onNavigateToEditBike = { _ -> },
            onNavigateToAddBike = {},
            onNavigateToBikeDetail = {})
    }
}