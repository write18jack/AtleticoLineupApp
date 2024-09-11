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
    alias(libs.plugins.com.android.library) apply false

}
true // Needed to make the Suppress annotation work for the plugins block