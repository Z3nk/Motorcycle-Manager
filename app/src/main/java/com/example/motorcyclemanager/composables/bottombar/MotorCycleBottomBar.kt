package com.example.motorcyclemanager.composables.bottombar

import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.motorcyclemanager.BikeDetail
import com.example.motorcyclemanager.Home
import com.example.motorcyclemanager.Settings
import com.example.motorcyclemanager.composables.bottombar.model.BottomNavItem

@Composable
fun MotorCycleBottomBar(navController: NavHostController) {
    val navItems = listOf(
        BottomNavItem("Home", Icons.Default.Home, Home),
        BottomNavItem("Profile", Icons.Default.Person, BikeDetail),
        BottomNavItem("Settings", Icons.Default.Settings, Settings)
    )
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    BottomAppBar(
        actions = {
            navItems.forEach { item ->
                IconButton(
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (item.route::class.qualifiedName == currentRoute) {
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
                modifier = Modifier.offset() // Décalage pour alignement visuel
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )
}
