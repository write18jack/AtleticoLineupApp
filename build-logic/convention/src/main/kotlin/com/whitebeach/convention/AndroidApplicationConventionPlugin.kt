package com.whitebeach.convention

import com.android.build.api.dsl.ApplicationExtension
import com.whitebeach.support.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")
            apply(plugin = "org.jetbrains.kotlin.android")
//            apply(plugin = "com.dropbox.dependency-guard")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35
//                @Suppress("UnstableApiUsage")
//                testOptions.animationsDisabled = true
//                configureGradleManagedDevices(this)
            }
//            extensions.configure<ApplicationAndroidComponentsExtension> {
//                configurePrintApksTask(this)
//                configureBadgingTasks(extensions.getByType<BaseExtension>(), this)
//            }
        }
    }
}