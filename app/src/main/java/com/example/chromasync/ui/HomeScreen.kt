package com.example.chromasync.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chromasync.data.models.ThemeProfile

open class ThemeScreen(val route: String) {
    object HomeScreen: ThemeScreen("home")
    object ListScreen: ThemeScreen("list")
    object SettingScreen: ThemeScreen("setting")
}

@Composable
fun ThemeNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ThemeScreen.HomeScreen.route) {
        composable(ThemeScreen.HomeScreen.route) {
            HomeScreenViews(onSaveTheme = {})
        }
    }

}