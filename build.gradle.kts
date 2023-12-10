//buildscript {
//    val compose_version by extra("1.5.0")
//
//    repositories {
//        google()
//        mavenCentral()
//        gradlePluginPortal()
//    }
//    dependencies {
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
//        classpath("com.android.tools.build:gradle:8.1.1")
//        classpath("com.google.dagger:hilt-android-gradle-plugin:2.47")
//        classpath("com.google.gms:google-services:4.4.0")
//    }
//    tasks.register("clean", Delete::class) {
//        delete(rootProject.buildDir)
//    }
//}
buildscript {
    dependencies {
        classpath(libs.google.services)
    }
}


// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.3.15" apply false

}
true // Needed to make the Suppress annotation work for the plugins block