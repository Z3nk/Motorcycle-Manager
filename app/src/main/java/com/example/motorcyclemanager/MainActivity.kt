package com.example.motorcyclemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.motorcyclemanager.ui.theme.MotorcycleManagerTheme
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable

object BikeDetail

@Serializable
object Settings

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

    Scaffold(bottomBar = { MotorCycleBottomBar(navController) }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            NavHost(navController = navController, startDestination = Home) {
                composable<Home> { backStackEntry ->
                    val home: Home = backStackEntry.toRoute()
                    HomeScreen(onNavigateToBikeDetail = {
                        navController.navigate(route = BikeDetail)
                    })
                }
                composable<BikeDetail> {
                    BikeDetailScreen(onNavigateToHomeScreen = {
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

data class BottomNavItem(val label: String, val icon: ImageVector, val route: Any)

@Composable
fun MotorCycleBottomBar(navController: NavHostController) {
    val navItems = listOf(
        BottomNavItem("Home", Icons.Default.Home, Home),
        BottomNavItem("Profile", Icons.Default.Person, BikeDetail),
        BottomNavItem("Settings", Icons.Default.Settings, Settings)
    )
    val selectedItem = remember { mutableIntStateOf(0) }
    BottomAppBar(
        actions = {
            navItems.forEachIndexed { index, item ->
                IconButton(
                    onClick = {
                        selectedItem.intValue = index
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selectedItem.intValue == index) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            // Bouton d'action flottant intégré
            FloatingActionButton(
                onClick = { /* Action du FAB */ },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.offset(y = 16.dp) // Décalage pour alignement visuel
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )
}

@Composable
fun HomeScreen(
    onNavigateToBikeDetail: () -> Unit,
) {
    Column {
        Text("Home Page")
        Button(onClick = { onNavigateToBikeDetail() }) {
            Text("Go to Bike")
        }
    }
}

@Composable
fun BikeDetailScreen(onNavigateToHomeScreen: () -> Unit) {

    Column {
        Text("Bike Page")
        Button(onClick = { onNavigateToHomeScreen() }) {
            Text("Go to Home")
        }
    }
}

@Composable
fun SettingsScreen(onNavigateToHomeScreen: () -> Unit) {

    Column {
        Text("SettingsScreen Page")
        Button(onClick = { onNavigateToHomeScreen() }) {
            Text("Go to Home")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MotorcycleManagerTheme {
        Greeting("Android")
    }
}