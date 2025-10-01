package fr.zunkit.motorcyclemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import fr.zunkit.motorcyclemanager.design.theme.MotorcycleManagerTheme
import fr.zunkit.motorcyclemanager.presentation.addbike.AddBikeScreen
import fr.zunkit.motorcyclemanager.presentation.addcheck.AddOrUpdateCheckScreen
import fr.zunkit.motorcyclemanager.presentation.addconsumable.AddOrUpdateConsumableScreen
import fr.zunkit.motorcyclemanager.presentation.addhour.AddHourScreen
import fr.zunkit.motorcyclemanager.presentation.bikedetails.BikeDetailsScreen
import fr.zunkit.motorcyclemanager.presentation.home.HomeScreen
import fr.zunkit.motorcyclemanager.presentation.settings.ui.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint
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
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(bottomBar = {  }) { padding ->
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
                composable<Settings> {
                    SettingsScreen(onNavigateToHomeScreen = {
                        navController.navigate(
                            route = Home
                        )
                    })
                }
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