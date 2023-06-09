plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.createvc"
        minSdk = 22
        targetSdk = 33
        versionCode = 20
        versionName = "1.0"

        testInstrumentationRunner = ("androidx.test.runner.AndroidJUnitRunner")
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {

        debug {
            applicationIdSuffix = ".debug"
            manifestPlaceholders["appName"] = "Debug"
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = false
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    kotlinOptions {
        freeCompilerArgs += "-Xcontext-receivers"
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    namespace = "com.example.atleticolineupapp"
}

kapt {
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {

    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.appcompat:appcompat-resources:1.6.1")
    implementation("androidx.compose.runtime:runtime:1.4.3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation ("androidx.compose.foundation:foundation:1.4.3")
    implementation ("androidx.compose.foundation:foundation-layout:1.4.3")
    implementation ("androidx.compose.animation:animation:1.4.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")

    //google
    implementation("com.google.android.material:material:1.9.0")

    //Room
    implementation("androidx.room:room-ktx:2.5.1")
    implementation("androidx.room:room-runtime:2.5.1")
    kapt("androidx.room:room-compiler:2.5.1")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation ("dev.shreyaspatil:capturable:1.0.3")
    implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation("io.coil-kt:coil-compose:2.3.0")

    //implementation("coreLibraryDesugaring \"com.android.tools:desugar_jdk_libs:1.1.1\"")

//    androidTestImplementation "junit:junit:4.13.2"
//    androidTestImplementation "androidx.test:core:1.5.0"
//    androidTestImplementation "androidx.test:runner:1.5.2"
//    androidTestImplementation "androidx.test:rules:1.5.0"
//    androidTestImplementation "androidx.test.espresso:espresso-core:3.5.1"
//    androidTestImplementation "androidx.test.ext:junit-ktx:1.1.5"
//    androidTestImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
//    androidTestImplementation "com.google.dagger:hilt-android:2.45"
//    androidTestImplementation "com.google.dagger:hilt-android-testing:2.45"
//    kaptAndroidTest "com.google.dagger:hilt-compiler:2.45"
}