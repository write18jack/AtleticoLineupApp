import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}
group = "com.whitebeach.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "com.whitebeach.convention.android.application.compose"
            implementationClass = "com.whitebeach.convention.AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "com.whitebeach.convention.android.application"
            implementationClass = "com.whitebeach.convention.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "com.whitebeach.convention.android.library"
            implementationClass = "com.whitebeach.convention.AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "com.whitebeach.convention.android.library.compose"
            implementationClass = "com.whitebeach.convention.AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "com.whitebeach.convention.android.feature"
            implementationClass = "com.whitebeach.convention.AndroidFeatureConventionPlugin"
        }
        register("hilt") {
            id = "com.whitebeach.convention.hilt"
            implementationClass = "com.whitebeach.convention.HiltConventionPlugin"
        }
        register("jvmLibrary") {
            id = "com.whitebeach.convention.jvm.library"
            implementationClass = "com.whitebeach.convention.JvmLibraryConventionPlugin"
        }
    }
}
