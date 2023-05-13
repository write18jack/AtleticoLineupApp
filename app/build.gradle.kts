plugins {
    alias(libs.plugins.android.app)
    //alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {

    compileSdk = 33

    defaultConfig {
        applicationId = ("com.example.createvc")
        minSdk = (28)
        targetSdk = (33)
        versionCode = (1)
        versionName = ("1.0")

        testInstrumentationRunner = ("androidx.test.runner.AndroidJUnitRunner")
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    
    implementation("androidx.compose.material:material:1.4.1")
    implementation("androidx.compose.material3:material3:1.1.0-alpha07")
    implementation(libs.bundles.android.ui)
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.1")
    implementation("org.burnoutcrew.composereorderable:reorderable:0.9.6")

    //test
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.bundles.android.debug.test)

    //google
    implementation("com.google.android.material:material:1.8.0")

    //Room
    implementation("androidx.room:room-ktx:2.5.0")
    implementation("androidx.room:room-runtime:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")
}