package com.scrymz.bitebuddy.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.scrymz.bitebuddy.BuildConfig

/**
 * Banner Ad Composable with loading and error states
 * Use this anywhere in your app where you need a banner ad
 */
@Composable
fun BannerAds(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val bannerId = BuildConfig.BANNER_AD_ID

    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    Log.d("BannerAd", "Initializing Banner Ad with ID: $bannerId")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { ctx ->
                AdView(ctx).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = bannerId

                    adListener = object : AdListener() {
                        override fun onAdLoaded() {
                            super.onAdLoaded()
                            isLoading = false
                            hasError = false
                            Log.d("BannerAd", "Ad loaded successfully")
                        }

                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            super.onAdFailedToLoad(adError)
                            isLoading = false
                            hasError = true
                            errorMessage = adError.message
                            Log.e("BannerAd", "Ad failed to load: ${adError.message}")
                            Log.e("BannerAd", "Error code: ${adError.code}")
                            Log.e("BannerAd", "Error domain: ${adError.domain}")
                        }

                        override fun onAdOpened() {
                            super.onAdOpened()
                            Log.d("BannerAd", "Ad opened")
                        }

                        override fun onAdClicked() {
                            super.onAdClicked()
                            Log.d("BannerAd", "Ad clicked")
                        }

                        override fun onAdClosed() {
                            super.onAdClosed()
                            Log.d("BannerAd", "Ad closed")
                        }

                        override fun onAdImpression() {
                            super.onAdImpression()
                            Log.d("BannerAd", "Ad impression recorded")
                        }
                    }

                    Log.d("BannerAd", "Starting to load ad with ID: $bannerId")
                    loadAd(AdRequest.Builder().build())
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Show loading indicator on top of the AdView while loading
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(8.dp),
                strokeWidth = 2.dp
            )
        }

        // Show error message if ad failed to load
        if (hasError) {
            Text(
                text = "Ad unavailable",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
