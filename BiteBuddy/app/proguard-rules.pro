# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep Kotlinx Serialization metadata and serializers

-keepclassmembers class ** {
    @kotlinx.serialization.Serializable *;
}
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class **$Companion {
    kotlinx.serialization.KSerializer serializer(...);
}
-keepattributes *Annotation*

# Keep all navigation route classes (they use @Serializable for type-safe navigation)
-keep @kotlinx.serialization.Serializable class com.scrymz.bitebuddy.presentation.navigation.routes.** { *; }
-keepclassmembers @kotlinx.serialization.Serializable class com.scrymz.bitebuddy.presentation.navigation.routes.** {
    <fields>;
    <init>(...);
}

# Keep serializers for navigation routes
-keep class com.scrymz.bitebuddy.presentation.navigation.routes.**$$serializer { *; }

# Additional serialization support
-keepattributes InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep names of serialized classes
-keepnames @kotlinx.serialization.Serializable class **

