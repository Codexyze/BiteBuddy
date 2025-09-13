package com.scrymz.bitebuddy.presentation.navigation.mainapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.scrymz.bitebuddy.presentation.navigation.routes.HOMESCREEN
import com.scrymz.bitebuddy.presentation.screens.HomeScreen

@Composable
fun BiteBuddy(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = HOMESCREEN ){
        composable<HOMESCREEN>{
            HomeScreen(navController = navController)
        }
    }
    
}