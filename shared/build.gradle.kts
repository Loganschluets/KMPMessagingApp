import com.android.build.api.dsl.Packaging
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    /*
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    //id("dev.icerock.mobile.multiplatform")*/

    kotlin("plugin.serialization") version "1.9.10"
    kotlin("multiplatform")
    id("com.android.library")
    //id("kotlin-parcelize")
    //id("plugin-serialization") version "2.0.0"
    id("dev.icerock.moko.kswift")
    //id("dev.icerock.mobile.multiplatform-resources")
    //id("dev.icerock.mobile.multiplatform.cocoapods")
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    targetHierarchy.default()
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.addAll(
            "-P",
            "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=dev.icerock.moko.parcelize.Parcelize",
        )
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }


    androidTarget {
        compilations.all {
            kotlinOptions{
                jvmTarget = "17"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {

        /*
        val arch = when (it.konanTarget) {
            org.jetbrains.kotlin.konan.target.KonanTarget.IOS_ARM64 -> "iosarm64"
            org.jetbrains.kotlin.konan.target.KonanTarget.IOS_SIMULATOR_ARM64 -> "iossimulator64"
            org.jetbrains.kotlin.konan.target.KonanTarget.IOS_X64 -> "iosx64"
            else -> throw IllegalArgumentException(it.konanTarget.name + "not found")
        }*/
        val mvvmVersion = "0.16.1"
        it.binaries.framework {
            baseName = "shared"
            //isStatic = true
            embedBitcode("disable")
            //export("dev.icerock.moko:resources-$arch:0.24.3")

            export("dev.icerock.moko:mvvm-core:$mvvmVersion")
            export("dev.icerock.moko:mvvm-livedata:$mvvmVersion")
            export("dev.icerock.moko:mvvm-state:$mvvmVersion")

        }
        //changed main to gradle85
        it.compilations["main"].kotlinOptions.freeCompilerArgs += "-Xexport-kdoc"
    }


    sourceSets {

        all{
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCRefinement")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
            languageSettings.optIn("kotlin.contracts.ExperimentalContracts")
            languageSettings.optIn("kotlin.time.ExperimentalTime")

            languageSettings.optIn("kotlinx.serialization.ExperimentalSerializationApi")
        }

        commonMain.dependencies {
            implementation(libs.ktor.client.content.negotiation)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.client.resources)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.json)
            //implementation(libs.ktor.client.plugins)
            implementation(libs.ktor.client.logging)

            api("dev.icerock.moko:mvvm-core:0.16.1")
            api("dev.icerock.moko:mvvm-livedata:0.16.1")
            api("dev.icerock.moko:mvvm-state:0.16.1")

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)

        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }

    
    kswift{
        install(dev.icerock.moko.kswift.plugin.feature.PlatformExtensionFunctionsFeature){
            filter = excludeFilter(
                "PackageFunctionContext/dev.icerock.moko:mvvm-state/dev.icerock.moko.mvvm/TypeParameter(id=0)/asState/",
                "PackageFunctionContext/dev.icerock.moko:mvvm-state/dev.icerock.moko.mvvm/TypeParameter(id=0)/asState/whenNull:Class(name=kotlin/Function0)<Class(name=dev/icerock/moko/mvvm/ResourceState)<TypeParameter(id=0),TypeParameter(id=1)>>"
            )
        }
        install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature)
        includeLibrary("shared")
    }
}

afterEvaluate {
    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }
}

/*
kswift {
    installDevPods = true // Enable if using local testing and custom CocoaPods configuration.
}*/

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink>().matching {
    it.binary is org.jetbrains.kotlin.gradle.plugin.mpp.Framework
}.configureEach {
    doLast() {
        val xcodeSwiftDirectory = File(buildDir.parentFile.parentFile, "iosApp/Generated")

        /*if (!xcodeSwiftDirectory.exists()) {
            xcodeSwiftDirectory.mkdirs()
        }*/

        val swiftDirectory = destinationDirectory.get().dir("${binary.baseName}Swift").asFile
        xcodeSwiftDirectory.listFiles()?.forEach {
            if (swiftDirectory.listFiles()?.contains(it) != true) {
                it.delete()
            }
        }
        swiftDirectory.copyRecursively(xcodeSwiftDirectory, overwrite = true)
    }
}


android {
    namespace = "com.example.networkingapp"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}


