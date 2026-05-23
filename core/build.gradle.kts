plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt)

    alias(libs.plugins.ksp)
}

android {
    namespace = "am.mojtaba.armengo.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    // Core
    api(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Activity
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coil (Image Loader)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // DataStore
    implementation(libs.datastore)

    // Firebase
    api(platform(libs.firebase.bom))
    api("com.google.firebase:firebase-auth-ktx")
    api("com.google.firebase:firebase-firestore-ktx")
    api("com.google.firebase:firebase-analytics-ktx")
    api("com.google.firebase:firebase-messaging-ktx")

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Hilt
    api(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Hilt WorkManger
    api("androidx.hilt:hilt-work:1.3.0")
    ksp("androidx.hilt:hilt-compiler:1.3.0")


    api("androidx.hilt:hilt-navigation-compose:1.3.0")

//    Coroutine Worker
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    // Reorder LazyColumn
    implementation("sh.calvin.reorderable:reorderable:3.0.0")

    // generate voice
    implementation ("io.github.whitemagic2014:tts-edge-java:1.3.3")

    // Exoplayer
    api(libs.androidx.media3.exoplayer)
    api(libs.androidx.media3.exoplayer.dash)
    api(libs.androidx.media3.ui)

    // Testing
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("app.cash.turbine:turbine:1.0.0")
}