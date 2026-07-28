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
        versionCode = 7
        versionName = "1.0.4"

        testInstrumentationRunner = ("androidx.test.runner.AndroidJUnitRunner")

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
    implementation(project(":presentation"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.google.material)
}
