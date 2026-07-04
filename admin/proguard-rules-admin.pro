# ==================================================
# Crash Reporting
# ==================================================

-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile


# ==================================================
# Admin Module Classes
# ==================================================

-keep class am.mojtaba.armengo.admin.** { *; }


# ==================================================
# Network Libraries
# ==================================================

-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn okio.**

# --------------------------------------------------
# Third-party library warnings
# --------------------------------------------------

-dontwarn java.awt.**

-dontwarn javax.money.**
-dontwarn javax.ws.rs.**

-dontwarn org.glassfish.jersey.**
-dontwarn org.javamoney.moneta.**
-dontwarn org.joda.time.**

-dontwarn springfox.**