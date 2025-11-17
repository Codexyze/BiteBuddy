package com.scrymz.bitebuddy.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ProgressTrackerOnlyScreen(navController: NavController) {
    ProgressTrackerScreen(onBack = { navController.popBackStack() })
}

