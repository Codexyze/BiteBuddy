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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.scrymz.bitebuddy.presentation.navigation.routes.ADSSCREEN
import com.scrymz.bitebuddy.presentation.navigation.routes.PRIVACYPOLICYSCREEN
import com.scrymz.bitebuddy.presentation.navigation.routes.PROGRESSSCREEN

@Composable
fun MoreScreen(navController: NavController) {
    var showExerciseScreen by remember { mutableStateOf(false) }
    var showWaterIntakeScreen by remember { mutableStateOf(false) }

    if (showExerciseScreen) {
        ExerciseScreen()
    } else if (showWaterIntakeScreen) {
        WaterIntakeScreen()
    } else {
        Surface(color = MaterialTheme.colorScheme.background) {
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
                    ThemedMenuButton(
                        text = "Exercise Tracker",
                        icon = { Icon(imageVector = Icons.Outlined.FitnessCenter, contentDescription = "Exercise") }
                    ) { showExerciseScreen = true }

                    Spacer(modifier = Modifier.height(16.dp))

                    ThemedMenuButton(
                        text = "Water Intake",
                        icon = { Icon(imageVector = Icons.Outlined.WaterDrop, contentDescription = "Water") }
                    ) { showWaterIntakeScreen = true }

                    Spacer(modifier = Modifier.height(16.dp))

                    ThemedMenuButton(
                        text = "Progress Photos",
                        icon = { Icon(imageVector = Icons.Outlined.PhotoLibrary, contentDescription = "Progress") }
                    ) { navController.navigate(PROGRESSSCREEN) }

                    Spacer(modifier = Modifier.height(16.dp))

                    ThemedMenuButton(text = "Ads") { navController.navigate(ADSSCREEN) }

                    Spacer(modifier = Modifier.height(16.dp))

                    ThemedMenuButton(text = "Privacy Policy") { navController.navigate(PRIVACYPOLICYSCREEN) }
                }

                // Banner Ad at bottom
                BannerAds()
            }
        }
    }
}

@Composable
private fun ThemedMenuButton(
    text: String,
    icon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(60.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        if (icon != null) {
            icon()
            Spacer(modifier = Modifier.height(0.dp)) // replaced width spacer with zero-height; vertical layout uses Row? Actually Buttons use Row horizontal; keep minimal placeholder
        }
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}
