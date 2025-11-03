package com.scrymz.bitebuddy.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.scrymz.bitebuddy.presentation.navigation.routes.ADSSCREEN
import com.scrymz.bitebuddy.presentation.navigation.routes.PRIVACYPOLICYSCREEN
import com.scrymz.bitebuddy.presentation.navigation.routes.PROGRESSSCREEN

@Composable
fun SettingsScreen(navController: NavController) {
    var showExerciseScreen by remember { mutableStateOf(false) }
    var showWaterIntakeScreen by remember { mutableStateOf(false) }

    if (showExerciseScreen) {
        ExerciseScreen()
    } else if (showWaterIntakeScreen) {
        WaterIntakeScreen()
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { showExerciseScreen = true },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FitnessCenter,
                        contentDescription = "Exercise",
                        modifier = Modifier.height(24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Exercise Tracker", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { showWaterIntakeScreen = true },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.WaterDrop,
                        contentDescription = "Water",
                        modifier = Modifier.height(24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Water Intake", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate(PROGRESSSCREEN) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PhotoLibrary,
                        contentDescription = "Progress",
                        modifier = Modifier.height(24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Progress Photos", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate(ADSSCREEN) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(60.dp)
                ) {
                    Text("Ads", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate(PRIVACYPOLICYSCREEN)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(60.dp)
                ) {
                    Text("Privacy Policy", fontSize = 18.sp)
                }
            }

            // Banner Ad at bottom
            BannerAds()
        }
    }
}
