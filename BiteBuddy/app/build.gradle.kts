plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.21"
    id ("kotlin-kapt")
    alias(libs.plugins.google.gms.google.services)

}
//
//val admobAppId: String = project.findProperty("ADMOB_APP_ID") as? String ?: ""
//val interstitialAdId: String = project.findProperty("INTERSTITIAL_AD_ID") as? String ?: ""
val admobAppId: String = project.findProperty("ADMOB_APP_ID") as? String
    ?: throw GradleException("ADMOB_APP_ID not set in gradle.properties!")

val interstitialAdId: String = project.findProperty("INTERSTITIAL_AD_ID") as? String
    ?: throw GradleException("INTERSTITIAL_AD_ID not set in gradle.properties!")

val banner_AdsId: String = project.findProperty("BANNER_AD_ID") as? String
    ?: throw GradleException("BANNER_ADS_ID not set in gradle.properties!")

android {
    namespace = "com.scrymz.bitebuddy"
    compileSdk = 36

    defaultConfig {
        manifestPlaceholders["ADMOB_APP_ID"] = admobAppId
        buildConfigField("String", "ADMOB_APP_ID", "\"$admobAppId\"")
        buildConfigField("String", "INTERSTITIAL_AD_ID", "\"$interstitialAdId\"")
        buildConfigField("String", "BANNER_AD_ID", "\"$banner_AdsId\"")

        applicationId = "com.scrymz.bitebuddy"
        minSdk = 26
        targetSdk = 36
        versionCode = 12
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.firebase.inappmessaging.display)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //
    val room_version = "2.8.0"

    implementation("androidx.room:room-runtime:$room_version")

    kapt("androidx.room:room-compiler:$room_version") // Use kapt for Kotlin.
    implementation("androidx.room:room-ktx:$room_version")

    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //ExteND LIB
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    //Nav
    val nav_version = "2.8.3"
    implementation("androidx.navigation:navigation-compose:${nav_version}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")

    //ads
    implementation("com.google.android.gms:play-services-ads:24.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.9.0")

    //Coil for image loading
    implementation("io.coil-kt:coil-compose:2.5.0")

    implementation("androidx.core:core-splashscreen:1.2.0")

    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))

    // Firebase
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-inappmessaging-display-ktx")


}