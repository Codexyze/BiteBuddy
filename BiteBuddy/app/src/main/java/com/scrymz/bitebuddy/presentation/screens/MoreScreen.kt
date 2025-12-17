package com.scrymz.bitebuddy.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.scrymz.bitebuddy.presentation.navigation.routes.ADSSCREEN
import com.scrymz.bitebuddy.presentation.navigation.routes.CONSISTENCYTRACKERSCREEN
import com.scrymz.bitebuddy.presentation.navigation.routes.EXERCISESCREEN
import com.scrymz.bitebuddy.presentation.navigation.routes.PRIVACYPOLICYSCREEN
import com.scrymz.bitebuddy.presentation.navigation.routes.PROGRESSSCREEN
import com.scrymz.bitebuddy.presentation.navigation.routes.WATERINTAKESCREEN

@Composable
fun MoreScreen(navController: NavController) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
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
                ) { navController.navigate(EXERCISESCREEN) }

                Spacer(modifier = Modifier.height(16.dp))

                ThemedMenuButton(
                    text = "Water Intake",
                    icon = { Icon(imageVector = Icons.Outlined.WaterDrop, contentDescription = "Water") }
                ) { navController.navigate(WATERINTAKESCREEN) }

                Spacer(modifier = Modifier.height(16.dp))

                ThemedMenuButton(
                    text = "Consistency Tracker",
                    icon = { Icon(imageVector = Icons.Outlined.CalendarMonth, contentDescription = "Consistency") }
                ) { navController.navigate(CONSISTENCYTRACKERSCREEN) }

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
            Spacer(modifier = Modifier.fillMaxWidth(0.05f))
        }
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}
