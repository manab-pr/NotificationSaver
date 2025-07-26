package com.mpm.notificationsaver

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mpm.notificationsaver.NotificationEntity
import com.mpm.notificationsaver.ui.screens.NotificationDetailBottomSheet
import com.mpm.notificationsaver.ui.screens.NotificationList
import com.mpm.notificationsaver.ui.screens.NotificationScreenWithBottomBar
import com.mpm.notificationsaver.ui.screens.NotificationTopBar
import com.mpm.notificationsaver.ui.screens.SettingsScreen
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

// Navigation routes
sealed class Screen(val route: String) {
    object Notifications : Screen("notifications")
    object Settings : Screen("settings")
    object Profile : Screen("profile")
    object Privacy : Screen("privacy")
    object SoundVibration : Screen("sound_vibration")
    object WiFi : Screen("wifi")
    object Battery : Screen("battery")
    object Storage : Screen("storage")
}

@Composable
fun NotificationSaverApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Notifications.route
    ) {
        composable(Screen.Notifications.route) {
            NotificationScreenWithBottomBar(
                notifications = emptyList(), // Pass your notifications list here
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Notifications.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onNavigateToPrivacy = {
                    navController.navigate(Screen.Privacy.route)
                },
                onNavigateToSound = {
                    navController.navigate(Screen.SoundVibration.route)
                },
                onNavigateToWiFi = {
                    navController.navigate(Screen.WiFi.route)
                },
                onNavigateToBattery = {
                    navController.navigate(Screen.Battery.route)
                },
                onNavigateToStorage = {
                    navController.navigate(Screen.Storage.route)
                }
            )
        }

        // Add other screens as needed
        composable(Screen.Profile.route) {
            // ProfileScreen()
        }

        composable(Screen.Privacy.route) {
            // PrivacyScreen()
        }

        // ... other screens
    }
}

