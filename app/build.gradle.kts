plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.compose)
//    id("com.google.gms.google-services")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.whitebeach.atleticolineupapp"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.whitebeach.atleticolineupapp"
        minSdk = 30
        targetSdk = 34
        versionCode = 4
        versionName = "1.0.2"

        testInstrumentationRunner = ("androidx.test.runner.AndroidJUnitRunner")
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        compose = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            //isShrinkResources = true
            //signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.kotlin.stdlib)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.material)
    implementation(libs.material3)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.transport.runtime)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
//    implementation(libs.play.services.ads)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.capturable)
    implementation(libs.coil.compose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)

    implementation(project(":data"))
    implementation(project(":presentation"))
}
