package com.scrymz.bitebuddy

import android.Manifest
import android.os.Bundle
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.scrymz.bitebuddy.notification.createMusicNotificationChannel
import com.scrymz.bitebuddy.presentation.navigation.mainapp.BiteBuddy
import com.scrymz.bitebuddy.presentation.utils.InterstitialAdHelper
import com.scrymz.bitebuddy.ui.theme.BiteBuddyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // User denied notification permission. You might show a Snackbar/Toast from here if needed.
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        createMusicNotificationChannel(this)
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Preload interstitial ad for Progress Tracker feature
        InterstitialAdHelper.loadAd(this)

        setContent {
            BiteBuddyTheme {
                BiteBuddy()
            }
        }

        askNotificationPermission()
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS

            when {
                ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission already granted – nothing to do.
                }

                shouldShowRequestPermissionRationale(permission) -> {
                    // In a real app, show an educational UI here explaining why notifications are useful.
                    // For now, just request the permission again when this path is hit.
                    requestPermissionLauncher.launch(permission)
                }

                else -> {
                    // First time ask / "Don't ask again" not yet selected -> request directly.
                    requestPermissionLauncher.launch(permission)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up ad reference
        InterstitialAdHelper.destroy()
    }
}
