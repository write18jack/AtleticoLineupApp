package com.whitebeach.convention

import com.android.build.gradle.LibraryExtension
import com.whitebeach.support.configureGradleManagedDevices
import com.whitebeach.support.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.whitebeach.convention.android.library")
            apply(plugin = "com.whitebeach.convention.hilt")
            //apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

            extensions.configure<LibraryExtension> {
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
            }

            dependencies {
                "implementation"(project(":data"))

                "implementation"(libs.findLibrary("androidx.hilt.navigation.compose").get())
                "implementation"(libs.findLibrary("lifecycle.runtime.ktx").get())
                "implementation"(libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
//                "implementation"(libs.findLibrary("androidx.navigation.compose").get())
//                "implementation"(libs.findLibrary("androidx.tracing.ktx").get())
                "implementation"(libs.findLibrary("kotlinx.serialization.json").get())

//                "testImplementation"(libs.findLibrary("androidx.navigation.testing").get())
//                "androidTestImplementation"( libs.findLibrary("androidx.lifecycle.runtimeTesting").get())
            }
        }
    }
}