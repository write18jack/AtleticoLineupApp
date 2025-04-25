plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.whitebeach.data"
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
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.ui)
    implementation(platform(libs.compose.bom))
    implementation(libs.converter.moshi)
    implementation(libs.converter.gson)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.compiler)

    androidTestImplementation(libs.espresso.core)
}