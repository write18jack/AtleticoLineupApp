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

        implementation(libs.core.ktx)
        implementation(libs.appcompat)
        implementation(libs.google.material)
        implementation(libs.kotlin.stdlib)
        implementation(libs.kotlin.reflect)
        implementation(libs.androidx.material)
        implementation(libs.material3)
        implementation(libs.androidx.constraintlayout.compose)
        implementation(libs.capturable)
        implementation(libs.coil.compose)
        implementation(libs.hilt.android)
        implementation(libs.play.services.ads)
        implementation(libs.retrofit)
        implementation(libs.converter.gson)

        testImplementation(libs.junit)
        testImplementation(libs.ui.test.junit4)
        testImplementation(libs.kotlinx.coroutines.test)
        testImplementation(libs.mockk)
        testImplementation(libs.androidx.core.testing)
        testImplementation(libs.robolectric)

        androidTestImplementation(libs.junit)
        androidTestImplementation(libs.androidx.test.ext.junit)
        androidTestImplementation(libs.ui.test.junit4)
        androidTestImplementation(libs.espresso.core)
        debugImplementation(libs.ui.test.manifest)

        implementation(project(":data"))
    }
}