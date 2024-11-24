plugins {

    //trick: for the same plugin versions in all sub-modules
    /*alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    //alias("dev.icerock.mobile.multiplatform.kswift") version "0.13.0"*/
    id("com.android.application").version("8.7.0").apply(false)
    id("com.android.library").version("8.7.0").apply(false)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin").version("2.0.1").apply(false)
    kotlin("android").version("2.0.0").apply(false)
    kotlin("multiplatform").version("1.9.10").apply(false)
    kotlin("plugin.serialization").version("1.9.10").apply(false)

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

buildscript{
    dependencies{
        //classpath("dev.icerock.moko:kswift-gradle-plugin:0.7.0")
        classpath(libs.kswift.gradle.plugin)
    }
}

allprojects{
    repositories{
        mavenCentral()
        maven { url = uri("https://maven.google.com") }
        google()
        maven { url = uri("https://jitpack.io") }
    }
}