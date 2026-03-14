plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.gms.google.services)
    alias(libs.plugins.hilt.plugin)
    kotlin("kapt")
}

android {
    namespace = "com.appricut.easylezo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.appricut.easylezo"
        minSdk = 24
        targetSdk = 35
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
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation( platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation ("com.google.firebase:firebase-analytics-ktx")

    // Compose
    implementation ("androidx.compose.ui:ui:1.5.0")
    implementation ("androidx.compose.material3:material3:1.2.0")
    implementation ("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.datastore:datastore-preferences:1.1.1")

//    Reorder Lazy Column
    implementation("sh.calvin.reorderable:reorderable:3.0.0")
    implementation("org.burnoutcrew.composereorderable:reorderable:0.9.6")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation ("androidx.activity:activity-compose:1.8.0")

    //      image loader like picasso
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    kapt("androidx.room:room-compiler:2.6.1")

    implementation ("com.google.dagger:hilt-android:2.51")
    kapt ("com.google.dagger:hilt-android-compiler:2.51")


    // GSON
    implementation("com.google.code.gson:gson:2.10.1")
    // برای ViewModel‌ها
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
//  for Pager
    implementation("androidx.compose.foundation:foundation:1.6.0")
}
