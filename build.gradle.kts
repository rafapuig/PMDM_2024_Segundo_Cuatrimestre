// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    //Para Room
    //id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
    alias(libs.plugins.google.devtools.ksp) apply false



    //Room Gradle Plugin
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    //id("androidx.room") version "$room_version" apply false


}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}