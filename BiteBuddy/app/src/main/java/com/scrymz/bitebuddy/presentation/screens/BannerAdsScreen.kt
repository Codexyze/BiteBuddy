package com.scrymz.bitebuddy.presentation.screens

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

    val targetHeight by animateDpAsState(
        targetValue = if (hasError) 0.dp else 60.dp,
        label = "banner_collapse"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(targetHeight)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        if (!hasError) {
            AndroidView(
                factory = { ctx ->
                    AdView(ctx).apply {
                        setAdSize(AdSize.BANNER)
                        adUnitId = bannerId

                        adListener = object : AdListener() {
                            override fun onAdLoaded() {
                                isLoading = false
                                hasError = false
                            }

                            override fun onAdFailedToLoad(adError: LoadAdError) {
                                isLoading = false
                                hasError = true
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

                        loadAd(AdRequest.Builder().build())
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(8.dp),
                    strokeWidth = 2.dp
                )
            }
        }
    }
}
