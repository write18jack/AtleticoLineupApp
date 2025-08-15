plugins {
    alias(libs.plugins.atletico.android.library)
    alias(libs.plugins.atletico.hilt)
}

android {
    namespace = "com.whitebeach.data"
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
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.converter.gson)
    implementation(libs.converter.moshi)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
//    testImplementation(libs.hilt.android.testing)
//    kspTest(libs.hilt.compiler)

}
