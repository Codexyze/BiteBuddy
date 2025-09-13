package com.scrymz.bitebuddy.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.scrymz.bitebuddy.presentation.navigation.routes.ADSSCREEN

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            // TODO: Navigate or perform Ads action
            navController.navigate(ADSSCREEN)
        }, modifier = Modifier.fillMaxWidth(0.85f)) {
            Text("Ads")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // TODO: Navigate or perform Privacy Policy action
        }) {
            Text("Privacy Policy")
        }
    }
}
