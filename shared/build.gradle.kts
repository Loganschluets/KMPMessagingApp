
plugins {
    kotlin("multiplatform") version "2.0.0"
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version "2.0.0"
    id("com.android.library")
    id("dev.icerock.moko.kswift")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }

    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }


    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()


    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "MultiPlatformLibrary"

            // this fixes firestore, but breaks MOKO
            // isStatic = true
            export("dev.icerock.moko:mvvm-core:0.16.1")
            export("dev.icerock.moko:mvvm-livedata:0.16.1")
            export("dev.icerock.moko:mvvm-state:0.16.1")
        }
    }


    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(libs.ktor.client.content.negotiation)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.client.resources)
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.client.json)
                //implementation(libs.ktor.client.plugins)
                implementation(libs.ktor.client.logging)

                api(libs.shareResources)
                api("dev.icerock.moko:mvvm-core:0.16.1")
                api("dev.icerock.moko:mvvm-livedata:0.16.1")
                api("dev.icerock.moko:mvvm-state:0.16.1")
            }

        }

        val commonTest by getting {

            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
    }
}


android {
    namespace = "com.example.networkingapp"
    compileSdk = 33
    defaultConfig {
        minSdk = 29
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
kswift {
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature)
}







