plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.21"
    id ("kotlin-kapt")

}
//
//val admobAppId: String = project.findProperty("ADMOB_APP_ID") as? String ?: ""
//val interstitialAdId: String = project.findProperty("INTERSTITIAL_AD_ID") as? String ?: ""
val admobAppId: String = project.findProperty("ADMOB_APP_ID") as? String
    ?: throw GradleException("ADMOB_APP_ID not set in gradle.properties!")

val interstitialAdId: String = project.findProperty("INTERSTITIAL_AD_ID") as? String
    ?: throw GradleException("INTERSTITIAL_AD_ID not set in gradle.properties!")

android {
    namespace = "com.scrymz.bitebuddy"
    compileSdk = 36

    defaultConfig {
        manifestPlaceholders["ADMOB_APP_ID"] = admobAppId
        buildConfigField("String", "ADMOB_APP_ID", "\"$admobAppId\"")
        buildConfigField("String", "INTERSTITIAL_AD_ID", "\"$interstitialAdId\"")

        applicationId = "com.scrymz.bitebuddy"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    implementation("com.google.android.gms:play-services-ads:24.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.9.0")


}