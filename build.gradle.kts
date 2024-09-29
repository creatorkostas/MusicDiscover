buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("com.google.firebase:perf-plugin:1.4.2")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val room_version = "2.6.1"
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
//    id("androidx.room") version "$room_version" apply false
    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
//    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false

    //id("org.jetbrains.kotlin.kapt") version "1.9.23"
    //androidx.room:room-runtime:$room_version")
    //id("org.jetbrains.kotlin.jvm") version "1.9.21" apply false
}