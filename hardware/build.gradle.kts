plugins {
    id(Plugins.javaLibrary)
    id(Plugins.kotlin)
}

configure<JavaPluginExtension> {
    sourceCompatibility = Versions.sourceCompatibility
    targetCompatibility = Versions.targetCompatibility
}

dependencies {
    implementation(Libs.kotlinStdlib)
    implementation(Libs.coroutines)
    implementation(Libs.arrowCore)
}
