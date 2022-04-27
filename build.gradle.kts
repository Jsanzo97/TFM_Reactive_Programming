import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.gradleVersions) version Versions.gradleVersions
}

buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath(BuildTools.androidGradlePlugin)
        classpath(BuildTools.kotlinGradlePlugin)
        classpath(BuildTools.gradleVersions)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = Versions.targetCompatibility.toString()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

//Configuration for gradle-versions-plugin to avoid non-release versions
//to run: gradlew dependencyUpdates
tasks {
    "dependencyUpdates"(DependencyUpdatesTask::class) {
        resolutionStrategy {
            componentSelection {
                all {
                    val rejected = listOf("alpha", "cr", "m", "preview", "eap")
                        .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-]*") }
                        .any { it.matches(candidate.version) }
                    if (rejected) {
                        reject("Release candidate")
                    }
                }
            }
        }
    }
}
