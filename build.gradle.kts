buildscript {
    extra.apply {
        set("compose_version", "1.4.3")
    }
}

plugins {
//    `java-gradle-plugin`
//    `kotlin-dsl`
//    `kotlin-dsl-precompiled-script-plugins`
    alias(libs.plugins.android.app) apply false
    //alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android) apply false
    id ("com.github.ben-manes.versions") version ("0.41.0")
    id ("nl.littlerobots.version-catalog-update") version ("0.6.0")
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}
