plugins {
    alias(libs.plugins.atletico.android.feature)
    alias(libs.plugins.atletico.android.library.compose)
}

android {
    namespace = "com.whitebeach.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }

    dependencies {
        implementation(project(":domain"))

        implementation(libs.androidx.compose.material.icons.extended)
        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.constraintlayout.compose)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(libs.androidx.lifecycle.runtime.compose)
        implementation(libs.androidx.hilt.navigation.compose)

        testImplementation(libs.junit)
        testImplementation(libs.kotlinx.coroutines.test)
    }
}