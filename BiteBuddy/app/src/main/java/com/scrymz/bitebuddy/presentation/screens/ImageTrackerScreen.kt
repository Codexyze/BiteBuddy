package com.scrymz.bitebuddy.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ImageTrackerScreen(navController: NavController) {
    ImageMapperScreen(onBack = { navController.popBackStack() })
}
