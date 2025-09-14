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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.scrymz.bitebuddy.presentation.navigation.routes.ADSSCREEN
import com.scrymz.bitebuddy.presentation.navigation.routes.PRIVACYPOLICYSCREEN

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate(ADSSCREEN) },
            modifier = Modifier
                .fillMaxWidth(0.9f)      // slightly bigger width
                .height(60.dp)           // increase height
        ) {
            Text("Ads", fontSize = 18.sp) // optional: make text bigger
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
}
