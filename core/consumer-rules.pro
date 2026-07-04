# ==================================================
# Model Classes
# ==================================================
# Prevent obfuscation of domain models, remote models,
# and Room entities used by Firebase, Gson, and Room.

-keep class am.mojtaba.armengo.core.domain.model.** { *; }
-keep class am.mojtaba.armengo.core.data.remote.model.** { *; }
-keep class am.mojtaba.armengo.core.data.local.entity.** { *; }


# ==================================================
# Reflection & Generic Type Information
# ==================================================
# Preserve annotations, generic type information,
# and class metadata required by Gson, Firebase,
# Retrofit, and other reflection-based libraries.

-keepattributes Signature,*Annotation*,EnclosingMethod,InnerClasses

-dontwarn sun.misc.Unsafe.**


# ==================================================
# Retrofit
# ==================================================
# Preserve Retrofit runtime classes and suppress
# warnings related to Retrofit internals.

-keep class retrofit2.** { *; }

-keepattributes ElementPrecision,RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations


# ==================================================
# Gson
# ==================================================
# Preserve Gson classes and fields annotated with
# @SerializedName for JSON serialization/deserialization.

-keep class com.google.gson.** { *; }

-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}


# ==================================================
# Room Database
# ==================================================
# Preserve Room database classes used at runtime.

-dontwarn androidx.room.**

-keep class * extends androidx.room.RoomDatabase
-keep class * implements androidx.room.RoomOpenHelper