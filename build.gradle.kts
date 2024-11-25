plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.5.0").apply(false)
    id("com.android.library").version("8.5.0").apply(false)
    kotlin("android").version("2.0.0").apply(false)
    kotlin("multiplatform").version("2.0.0").apply(false)
    id("com.google.gms.google-services").version("4.3.15").apply(false)
    id("dev.icerock.moko.kswift").version("0.6.1").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
