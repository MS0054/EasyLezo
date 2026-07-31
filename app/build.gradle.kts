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
    compileSdk = 36

    defaultConfig {
        applicationId = "am.mojtaba.armengo"
        minSdk = 24
        targetSdk = 36
        versionCode = 8
        versionName = "0.0.8"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//       consumerProguardFiles("consumer-rules.pro")
    }

    signingConfigs {
        val keystorePath = System.getenv("KEYSTORE_FILE") ?: "release.jks"
        val keystoreFile = file(keystorePath)

        if (keystoreFile.exists()) {
            create("release") {
                storeFile = keystoreFile
                storePassword = System.getenv("KEYSTORE_PASSWORD") ?: "Local_Pass"
                keyAlias = System.getenv("KEY_ALIAS") ?: "Local_Alias"
                keyPassword = System.getenv("KEY_PASSWORD") ?: "Local_Key_Pass"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // این خط بسیار مهم است تا بیلد ریلیز از کانفیگ بالا استفاده کند
            signingConfig = signingConfigs.findByName("release") ?: signingConfigs.getByName("debug")
        }

        debug {
//            isMinifyEnabled = false
//            isShrinkResources = false
//            isDebuggable = true

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
//    testImplementation(libs.junit)

//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
}