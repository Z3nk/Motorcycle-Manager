package com.example.motorcyclemanager

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
import com.example.motorcyclemanager.composables.bottombar.MotorCycleBottomBar
import com.example.motorcyclemanager.design.theme.MotorcycleManagerTheme
import com.example.motorcyclemanager.presentation.addbike.AddBikeScreen
import com.example.motorcyclemanager.presentation.bikedetails.BikeDetailsScreen
import com.example.motorcyclemanager.presentation.home.HomeScreen
import com.example.motorcyclemanager.presentation.settings.ui.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object AddBike

@Serializable
data class BikeDetail(val id: Long)

@Serializable
object Settings

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MotorcycleManagerTheme {
                MotorcycleManager()
            }
        }
    }
}

@Composable
private fun MotorcycleManager() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(bottomBar = { MotorCycleBottomBar(navController) }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            NavHost(navController = navController, startDestination = Home) {
                composable<Home> { backStackEntry ->
                    val home: Home = backStackEntry.toRoute()
                    HomeScreen(
                        onNavigateToBikeDetail = { id ->
                            navController.navigate(route = BikeDetail(id))
                        },
                        onNavigateToAddBike = {
                            navController.navigate(route = AddBike)
                        })
                }
                composable<AddBike> {
                    AddBikeScreen(onNavigateToHomeScreen = {
                        navController.navigate(Home) {
                            popUpTo(Home) {
                                saveState = true
                            }; launchSingleTop = true
                        }
                    })
                }
                composable<BikeDetail> { backStackEntry ->
                    val bikeDetail: BikeDetail = backStackEntry.toRoute()
                    BikeDetailsScreen(
                        bikeId = bikeDetail.id,
                        onNavigateToHomeScreen = {
                            navController.navigate(
                                route = Home
                            )
                        })
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