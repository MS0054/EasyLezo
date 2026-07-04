# ==================================================
# Crash Reporting
# ==================================================

-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile


# ==================================================
# Application Classes
# ==================================================
# Keep app module classes.

-keep class am.mojtaba.armengo.app.** { *; }


# ==================================================
# Hilt
# ==================================================

-dontwarn dagger.hilt.internal.**
-dontwarn androidx.hilt.navigation.compose.**


# ==================================================
# Splash Screen
# ==================================================

-dontwarn androidx.core.splashscreen.**


# ==================================================
# Coil
# ==================================================

-dontwarn coil.**
-dontwarn okhttp3.**

# ==================================================
# Missing Rules
# ==================================================

-dontwarn javax.ws.rs.**
-dontwarn org.glassfish.jersey.**