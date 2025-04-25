import org.gradle.kotlin.dsl.libs

plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose) apply false
    // Add the dependency for the Google services Gradle plugin
    //id("com.google.gms.google-services") version "4.3.15" apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}
true