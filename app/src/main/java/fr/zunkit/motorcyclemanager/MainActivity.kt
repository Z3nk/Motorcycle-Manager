package fr.zunkit.motorcyclemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import fr.zunkit.motorcyclemanager.design.theme.MotorcycleManagerTheme
import fr.zunkit.motorcyclemanager.presentation.addbike.AddBikeScreen
import fr.zunkit.motorcyclemanager.presentation.addcheck.AddOrUpdateCheckScreen
import fr.zunkit.motorcyclemanager.presentation.addconsumable.AddOrUpdateConsumableScreen
import fr.zunkit.motorcyclemanager.presentation.addhour.AddHourScreen
import fr.zunkit.motorcyclemanager.presentation.bikedetails.BikeDetailsScreen
import fr.zunkit.motorcyclemanager.presentation.home.HomeScreen
import fr.zunkit.motorcyclemanager.presentation.settings.ui.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class AddOrUpdateBike(val bikeId: Long? = null)

@Serializable
data class BikeDetail(val id: Long)

@Serializable
data class AddHour(val bikeId: Long)

@Serializable
data class AddOrUpdateConsumable(val bikeId: Long, val consumableId: Long? = null)

@Serializable
data class AddOrUpdateCheck(val bikeId: Long, val checkId: Long? = null)

@Serializable
object Settings

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MotorcycleManagerTheme(darkTheme = true) {
                MotorcycleManager()
            }
        }
    }
}

@Composable
private fun MotorcycleManager() {
    val navController = rememberNavController()
    var selectedMenu by remember { mutableIntStateOf(0) }

    Scaffold(bottomBar = {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        if (currentBackStackEntry.isCurrentRoute<Home>()) {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                NavigationBarItem(
                    selected = selectedMenu == 0,
                    onClick = { selectedMenu = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = {
                        Text(
                            stringResource(R.string.home),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedMenu == 1,
                    onClick = { selectedMenu = 1 },
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
    }) { padding ->
        if (selectedMenu == 0) {
            MainScreen(padding, navController)
        } else {
            SettingsScreen(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun MainScreen(
    padding: PaddingValues,
    navController: NavHostController
) {
    Column(modifier = Modifier.padding(padding)) {
        NavHost(navController = navController, startDestination = Home) {
            composable<Home> { backStackEntry ->
                HomeScreen(
                    onNavigateToBikeDetail = { id ->
                        navController.navigate(route = BikeDetail(id))
                    },
                    onNavigateToAddBike = {
                        navController.navigate(route = AddOrUpdateBike())
                    },
                    onNavigateToEditBike = { bike ->
                        navController.navigate(route = AddOrUpdateBike(bike.id))
                    })
            }
            composable<AddOrUpdateBike> { backStackEntry ->
                val addOrUpdateBike: AddOrUpdateBike = backStackEntry.toRoute()
                AddBikeScreen(
                    bikeId = addOrUpdateBike.bikeId,
                    onNavigateToHomeScreen = {
                        navController.navigate(Home) {
                            popUpTo(Home) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    })
            }
            composable<AddOrUpdateCheck> { backStackEntry ->
                val addOrUpdateCheck: AddOrUpdateCheck = backStackEntry.toRoute()
                AddOrUpdateCheckScreen(
                    bikeId = addOrUpdateCheck.bikeId,
                    checkId = addOrUpdateCheck.checkId,
                    goBackToBikeDetail = {
                        navController.navigate(BikeDetail(addOrUpdateCheck.bikeId)) {
                            popUpTo(BikeDetail(addOrUpdateCheck.bikeId)) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable<AddHour> { backStackEntry ->
                val addHour: AddHour = backStackEntry.toRoute()
                AddHourScreen(
                    bikeId = addHour.bikeId,
                    goBackToBikeDetail = {
                        navController.navigate(BikeDetail(addHour.bikeId)) {
                            popUpTo(BikeDetail(addHour.bikeId)) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable<AddOrUpdateConsumable> { backStackEntry ->
                val addOrUpdateConsumable: AddOrUpdateConsumable = backStackEntry.toRoute()
                AddOrUpdateConsumableScreen(
                    bikeId = addOrUpdateConsumable.bikeId,
                    consumableId = addOrUpdateConsumable.consumableId,
                    goBackToBikeDetail = {
                        navController.navigate(BikeDetail(addOrUpdateConsumable.bikeId)) {
                            popUpTo(BikeDetail(addOrUpdateConsumable.bikeId)) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<BikeDetail> { backStackEntry ->
                val bikeDetail: BikeDetail = backStackEntry.toRoute()
                BikeDetailsScreen(
                    bikeId = bikeDetail.id,
                    onNavigateToHomeScreen = {
                        navController.navigate(
                            route = Home
                        )
                    },
                    onNavigateToAddHourScreen = { bikeId ->
                        navController.navigate(
                            route = AddHour(bikeId)
                        )
                    },
                    onNavigateToAddConsumableScreen = { bikeId ->
                        navController.navigate(
                            route = AddOrUpdateConsumable(bikeId = bikeId)
                        )
                    },
                    onNavigateToAddCheckScreen = { bikeId ->
                        navController.navigate(
                            route = AddOrUpdateCheck(bikeId = bikeId)
                        )
                    },
                    onNavigateToEditCheckScreen = { checkId ->
                        navController.navigate(
                            route = AddOrUpdateCheck(bikeId = bikeDetail.id, checkId = checkId)
                        )
                    },
                    onNavigateToEditConsumableScreen = { consumableId ->
                        navController.navigate(
                            route = AddOrUpdateConsumable(
                                bikeId = bikeDetail.id,
                                consumableId = consumableId
                            )
                        )
                    },
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MotorcycleManagerTheme {
        MotorcycleManager()
    }
}

inline fun <reified T : Any> NavBackStackEntry?.isCurrentRoute(): Boolean {
    val route = this?.destination?.route ?: return false
    val expectedRoute = T::class.qualifiedName ?: return false
    return route.startsWith(expectedRoute)
}