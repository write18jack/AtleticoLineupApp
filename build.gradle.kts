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
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.45")
    }
    tasks.register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
