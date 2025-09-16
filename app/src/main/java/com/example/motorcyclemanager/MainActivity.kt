package com.example.motorcyclemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> { backStackEntry ->
            val home: Home = backStackEntry.toRoute()
            HomeScreen(home = home, onNavigateToBikeDetail = {
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
    }
}

@Composable
fun HomeScreen(
    home: Home,
    onNavigateToBikeDetail: () -> Unit,
) {
    Scaffold() { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("Home Page")
            Button(onClick = { onNavigateToBikeDetail() }) {
                Text("Go to Bike")
            }
        }
    }
}

@Composable
fun BikeDetailScreen(onNavigateToHomeScreen: () -> Unit) {
    Scaffold() { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("Bike Page")
            Button(onClick = { onNavigateToHomeScreen() }) {
                Text("Go to Home")
            }
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