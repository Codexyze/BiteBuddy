# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# ================================================================================================
# GENERAL RULES
# ================================================================================================

# Preserve line numbers for debugging stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep annotations
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# ================================================================================================
# ANDROID & ANDROIDX RULES
# ================================================================================================

# Keep Android components
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep view constructors
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# ================================================================================================
# ROOM DATABASE RULES
# ================================================================================================

# Keep all Room entities
-keep @androidx.room.Entity class * {*;}
-keep class * extends androidx.room.RoomDatabase {*;}

# Keep all DAOs
-keep @androidx.room.Dao class * {*;}
-keepclassmembers class * extends androidx.room.RoomDatabase {
    public abstract <methods>;
}

# Keep Room database classes
-keep class com.scrymz.bitebuddy.data.database.** {*;}
-keep class com.scrymz.bitebuddy.data.entity.** {*;}
-keep class com.scrymz.bitebuddy.data.dao.** {*;}

# Keep all entities with @Keep annotation
-keep @androidx.annotation.Keep class * {*;}

# ================================================================================================
# HILT / DAGGER RULES
# ================================================================================================

# Keep Hilt generated classes
-keep class dagger.** {*;}
-keep class javax.inject.** {*;}
-dontwarn dagger.internal.**

# Keep Hilt modules and injected classes
-keep @dagger.hilt.android.HiltAndroidApp class * {*;}
-keep @dagger.Module class * {*;}
-keep @dagger.hilt.InstallIn class * {*;}
-keep class * {
    @javax.inject.Inject <init>(...);
    @javax.inject.Inject <fields>;
    @javax.inject.Inject <methods>;
}

# Keep Hilt ViewModels
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * {*;}

# Keep DI modules and repositories
-keep class com.scrymz.bitebuddy.di.** {*;}
-keep class com.scrymz.bitebuddy.domain.repository.** {*;}
-keep class com.scrymz.bitebuddy.data.repoImpl.** {*;}
-keep class com.scrymz.bitebuddy.domain.usecases.** {*;}

# ================================================================================================
# JETPACK COMPOSE RULES
# ================================================================================================

# Keep Composable functions
-keep @androidx.compose.runtime.Composable class * {*;}
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}

# Keep Compose runtime classes
-keep class androidx.compose.runtime.** {*;}
-keep class androidx.compose.ui.** {*;}
-keep class androidx.compose.foundation.** {*;}
-keep class androidx.compose.material3.** {*;}
-keep class androidx.compose.material.** {*;}

# Keep ViewModel classes
-keep class * extends androidx.lifecycle.ViewModel {*;}
-keep class com.scrymz.bitebuddy.presentation.viewmodels.** {*;}

# ================================================================================================
# KOTLINX SERIALIZATION RULES
# ================================================================================================

# Keep serializable classes
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep @Serializable classes
-keep @kotlinx.serialization.Serializable class * {*;}
-keepclassmembers @kotlinx.serialization.Serializable class * {
    *** Companion;
    *** INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep Navigation routes
-keep class com.scrymz.bitebuddy.presentation.navigation.routes.** {*;}

# ================================================================================================
# GOOGLE ADMOB & PLAY SERVICES RULES
# ================================================================================================

# Keep AdMob classes
-keep class com.google.android.gms.ads.** {*;}
-keep class com.google.android.gms.internal.ads.** {*;}
-dontwarn com.google.android.gms.ads.**

# Keep WebView for ads
-keepclassmembers class * extends android.webkit.WebView {
    public *;
}
-keepclassmembers class * extends android.webkit.WebChromeClient {
    public void *(android.webkit.WebView, java.lang.String);
}

# Keep ad helper classes
-keep class com.scrymz.bitebuddy.presentation.utils.InterstitialAdHelper {*;}
-keep class com.scrymz.bitebuddy.presentation.screens.AdsScreen** {*;}
-keep class com.scrymz.bitebuddy.presentation.screens.BannerAdsScreen** {*;}

# ================================================================================================
# COIL IMAGE LOADING RULES
# ================================================================================================

# Keep Coil classes
-keep class coil.** {*;}
-keep interface coil.** {*;}
-dontwarn coil.**

# ================================================================================================
# KOTLIN & COROUTINES RULES
# ================================================================================================

# Keep Kotlin metadata
-keep class kotlin.Metadata {*;}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# Keep coroutines
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-keepclassmembers class kotlinx.coroutines.** {*;}

# Keep sealed classes
-keep class * extends kotlin.coroutines.Continuation

# ================================================================================================
# NAVIGATION COMPONENT RULES
# ================================================================================================

# Keep Navigation classes
-keep class androidx.navigation.** {*;}
-keepclassmembers class * extends androidx.navigation.Navigator {
    <init>(...);
}

# ================================================================================================
# DATA CLASSES & MODELS RULES
# ================================================================================================

# Keep data classes
-keepclassmembers class com.scrymz.bitebuddy.data.entity.** {
    <fields>;
    <init>(...);
}

# Keep constants
-keep class com.scrymz.bitebuddy.Constants.** {*;}

# Keep state classes
-keep class com.scrymz.bitebuddy.domain.StateHandeling.** {*;}
-keep class com.scrymz.bitebuddy.presentation.states.** {*;}

# ================================================================================================
# MAIN APPLICATION RULES
# ================================================================================================

# Keep MainActivity
-keep class com.scrymz.bitebuddy.MainActivity {*;}

# Keep BaseApplication
-keep class com.scrymz.bitebuddy.di.BaseApplicationClass {*;}

# Keep database helper
-keep class com.scrymz.bitebuddy.core.DataBaseOpenHelper {*;}

# ================================================================================================
# REFLECTION RULES
# ================================================================================================

# Keep classes accessed via reflection
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# ================================================================================================
# REMOVE LOGGING IN RELEASE
# ================================================================================================

# Remove logging code
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# ================================================================================================
# OPTIMIZATION SETTINGS
# ================================================================================================

# Enable aggressive optimization
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

# Optimization options
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

# Allow obfuscation
-repackageclasses ''
-allowaccessmodification

# ================================================================================================
# ADDITIONAL SAFETY RULES
# ================================================================================================

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelables
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# Keep Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
