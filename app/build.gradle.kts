import com.android.build.api.dsl.Packaging
import java.util.Properties

plugins {
    alias(libs.plugins.atletico.android.application)
    alias(libs.plugins.atletico.android.application.compose)
    alias(libs.plugins.atletico.hilt)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.whitebeach.atleticolineupapp"
    defaultConfig {
        applicationId = "com.whitebeach.atleticolineupapp"
        versionCode = 4
        versionName = "1.0.2"

        testInstrumentationRunner = ("androidx.test.runner.AndroidJUnitRunner")

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "API_KEY", properties.getProperty("API_KEY"))

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        debug {
            //applicationIdSuffix = NiaBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
        }
    }

    // 重複するファイルを除外する
    // 他のライセンスファイルで同様のエラーが出た場合も追記
    packaging {
        resources {
            pickFirsts += listOf(
                "META-INF/AL2.0",
                "META-INF/LGPL2.1"
            )
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.ui.tooling.preview)
    implementation(libs.transport.runtime)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.play.services.ads)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.google.services)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(project(":data"))
    implementation(project(":presentation"))
}
