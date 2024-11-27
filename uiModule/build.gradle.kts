plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "2.0.0"
    id ("kotlin-kapt")
}

android {
    namespace = "com.abisoft.uimodule"
    compileSdk = 34

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation (libs.androidx.navigation.compose)

    implementation (libs.material3)// Material3 kutubxonasi
    implementation (libs.ui) // Jetpack Compose uchun kerakli kutubxona
    implementation (libs.androidx.material) // Material kutubxonasi (agar kerak bo'lsa)
    implementation (libs.ui.tooling.preview) // Jetpack Composening davr
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json) // Eng so'nggi
    implementation(libs.logging.interceptor)

    implementation (libs.dagger)
    kapt(libs.dagger.compiler)

    implementation( libs.androidx.core.ktx.v1150)

    implementation(project(":app"))
}