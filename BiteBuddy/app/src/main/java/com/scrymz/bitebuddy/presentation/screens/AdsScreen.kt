package com.scrymz.bitebuddy.presentation.screens

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.scrymz.bitebuddy.BuildConfig


@Composable
fun InterstitialAdScreen() {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    val interstitialId = BuildConfig.INTERSTITIAL_AD_ID


    var interstitialAd by remember { mutableStateOf<InterstitialAd?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var loadFailed by remember { mutableStateOf(false) }

    // Load Interstitial safely
    fun loadAd() {
        isLoading = true
        loadFailed = false

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            interstitialId, // Test ID
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isLoading = false
                    loadFailed = false
                    Log.d("SafeInterstitialAd", "Ad Loaded")
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                    isLoading = false
                    loadFailed = true
                    Log.d("SafeInterstitialAd", "Failed: ${adError.message}")
                }
            }
        )
    }

    // Load ad on first composition
    LaunchedEffect(Unit) { loadAd() }

    // Clean up reference when Composable leaves
    DisposableEffect(Unit) {
        onDispose {
            interstitialAd = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Feedback by watching Ads",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        when {
            isLoading -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(8.dp))
                Text("Loading ad...")
            }
            loadFailed -> {
                Text("Ad failed to load", color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { loadAd() }) {
                    Text("Retry")
                }
            }
            else -> {
                Button(
                    onClick = {
                        activity?.let { interstitialAd?.show(it) }
                        interstitialAd = null // Reset after showing
                        loadAd() // Preload next ad
                    },
                    enabled = interstitialAd != null
                ) {
                    Text("Feedback Ad")
                }
            }
        }
    }
}
