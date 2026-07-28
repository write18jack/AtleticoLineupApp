package com.whitebeach.convention

import com.android.build.gradle.LibraryExtension
import com.whitebeach.support.configureGradleManagedDevices
import com.whitebeach.support.libs
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.whitebeach.convention.android.library")
            apply(plugin = "com.whitebeach.convention.android.library.compose")
            apply(plugin = "com.whitebeach.convention.hilt")

            extensions.configure<LibraryExtension> {
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
            }
        }
    }
}