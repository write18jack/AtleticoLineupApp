buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build", "gradle", "7.3.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
        classpath("com.android.tools.build:gradle:8.0.2")
    }


//plugins {
//    alias(libs.plugins.android.app) apply false
//    alias(libs.plugins.kotlin.android) apply false
//    id ("com.github.ben-manes.versions") version ("0.41.0")
//    id ("nl.littlerobots.version-catalog-update") version ("0.6.0")
//}

    tasks.register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }

//    subprojects {
//        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
//            kotlinOptions {
//                if (project.findProperty("enableComposeCompilerReports") == "true") {
//                    arrayOf("reports", "metrics").forEach {
//                        freeCompilerArgs = freeCompilerArgs + listOf(
//                            "-P",
//                            "plugin:androidx.compose.compiler.plugins.kotlin:${it}Destination=${project.buildDir.absolutePath}/compose_metrics"
//                        )
//                    }
//                }
//            }
//        }
    }
