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
data class Profile(val name: String)

@Serializable
object FriendsList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MotorcycleManagerTheme {
                ZManager()
            }
        }
    }
}

@Composable
private fun ZManager() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Profile("John Smith")) {
        composable<Profile> { backStackEntry ->
            val profile: Profile = backStackEntry.toRoute()
            ProfileScreen(profile = profile, onNavigateToFriendsList = {
                navController.navigate(route = FriendsList)
            })
        }
        composable<FriendsList> {
            FriendsListScreen(onNavigateToProfile = {
                navController.navigate(
                    route = Profile(name = "Aisha Devi")
                )
            })
        }
    }
}

@Composable
fun ProfileScreen(
    profile: Profile,
    onNavigateToFriendsList: () -> Unit,
) {
    Scaffold() { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("Profile for ${profile.name}")
            Button(onClick = { onNavigateToFriendsList() }) {
                Text("Go to Friends List")
            }
        }
    }
}

@Composable
fun FriendsListScreen(onNavigateToProfile: () -> Unit) {
    Scaffold() { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("Friends List")
            Button(onClick = { onNavigateToProfile() }) {
                Text("Go to Profile")
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