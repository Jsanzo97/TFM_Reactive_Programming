plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

android {
    compileSdkVersion(Versions.targetSdk)

    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        forEach {
            it.java.srcDir("src/${it.name}/kotlin")
        }
    }

    compileOptions {
        sourceCompatibility = Versions.sourceCompatibility
        targetCompatibility = Versions.targetCompatibility
    }
}

dependencies {
    implementation(project(":data"))
    implementation(Libs.kotlinStdlib)
    implementation(Libs.liveDataKtx)
}
