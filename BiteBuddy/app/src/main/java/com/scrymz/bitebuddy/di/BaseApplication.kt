package com.scrymz.bitebuddy.di

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Google Mobile Ads SDK
        MobileAds.initialize(this) { initializationStatus ->
            Log.d("MobileAds", "Initialization complete: ${initializationStatus.adapterStatusMap}")
        }
    }
}