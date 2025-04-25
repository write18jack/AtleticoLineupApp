plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.whitebeach.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 30

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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    dependencies {

        implementation(libs.core.ktx)
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.lifecycle.runtime.ktx)
        implementation(libs.kotlin.stdlib)
        implementation(libs.activity.compose)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(platform(libs.compose.bom))
        implementation(libs.ui)
        implementation(libs.ui.graphics)
        implementation(libs.ui.tooling.preview)
        implementation(libs.androidx.material)
        implementation(libs.material3)
        implementation(libs.androidx.constraintlayout.compose)
        implementation(libs.capturable)
        implementation(libs.coil.compose)
//        implementation(libs.play.services.ads)
        implementation(libs.retrofit)
        implementation(libs.converter.gson)
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)

        testImplementation(libs.junit)
        testImplementation(libs.ui.test.junit4)
        testImplementation(libs.kotlinx.coroutines.test)
        testImplementation(libs.mockk)
        testImplementation(libs.androidx.core.testing)
        testImplementation(libs.robolectric)
        androidTestImplementation(libs.junit)
        androidTestImplementation(libs.ui.test.junit4)
        androidTestImplementation(libs.espresso.core)
        androidTestImplementation(libs.junit.ext)
        androidTestImplementation(platform(libs.compose.bom))
        debugImplementation(libs.ui.test.manifest)
        debugImplementation(libs.ui.tooling)

        implementation(project(":data"))
    }
}
