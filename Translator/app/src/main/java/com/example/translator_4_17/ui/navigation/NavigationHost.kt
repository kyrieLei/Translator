package com.example.translator_4_17.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.translator_4_17.models.TranslationModel
import com.example.translator_4_17.ui.screen.HomeScreen
import com.example.translator_4_17.ui.screen.SettingsScreen

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    translationModel: TranslationModel
) {
    NavHost(navController = navHostController, "home") {
        composable("home") {
            HomeScreen(navHostController = navHostController, viewModel = translationModel)
        }
        composable("settings") {
            SettingsScreen(navController = navHostController)
        }
    }
}