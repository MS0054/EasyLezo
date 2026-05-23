plugins {
//    alias(libs.plugins.android.library)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.google.services)

    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "am.mojtaba.armengo.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "am.mojtaba.armengo"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        jvmToolchain(21)
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core"))


    // Activity
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ui)

    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coil (Image Loader)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")

    // Splash
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Testing
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}