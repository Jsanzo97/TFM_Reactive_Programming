import org.gradle.api.JavaVersion

object Plugins {
    const val android = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val javaLibrary = "java-library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlin = "kotlin"
    const val kapt = "kotlin-kapt"
    const val gradleVersions = "com.github.ben-manes.versions"
    const val navigationKotlin = "androidx.navigation.safeargs.kotlin"
}

object Versions {
    const val kotlin = "1.5.30"
    const val coroutines = "1.3.2"
    const val androidGradlePlugin = "3.5.1"
    const val gradleVersions = "0.25.0"

    const val minSdk = 23
    const val targetSdk = 29

    val sourceCompatibility = JavaVersion.VERSION_1_8
    val targetCompatibility = JavaVersion.VERSION_1_8

    const val appCompat = "1.1.0"
    const val coreKtx = "1.1.0"
    const val material = "1.1.0-alpha10"
    const val constraintLayout = "2.0.0-beta3"
    const val lifecycle = "2.1.0"
    const val liveData = "2.4.0"
    const val room = "2.4.0-rc01"
    const val navigation = "2.1.0"
    const val drawerLayout = "1.1.0-alpha03"

    const val arrow = "0.10.0"
    const val threeTenABP = "1.2.1"
    const val timber = "4.7.1"
    const val koin = "2.0.1"

    const val debugDb = "1.0.6"
}

object BuildTools {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val gradleVersions = "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersions}"
    const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
}

object Libs {
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveData}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val drawerLayout = "androidx.drawerlayout:drawerlayout:${Versions.drawerLayout}"
    const val threeTenABP = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenABP}"
    const val arrowCore = "io.arrow-kt:arrow-core-data:${Versions.arrow}"
    const val koin = "org.koin:koin-core:${Versions.koin}"
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinAndroidScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koinAndroidViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

}

object Kapt {
    const val lifecycle = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val room = "androidx.room:room-compiler:${Versions.room}"
}

object DebugLibs {
    const val debugDb = "com.amitshekhar.android:debug-db:${Versions.debugDb}"
}
