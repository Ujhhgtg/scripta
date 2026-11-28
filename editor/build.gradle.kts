plugins {
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinMultiplatform)
    `maven-publish`
}

group = "dev.ujhhgtg.scripta"
version = "0.1.0"

kotlin {
    jvmToolchain(21)

    android {
        namespace = "top.yukonga.scripta.editor"
        compileSdk {
            version = release(37) {
                minorApiLevel = 2
            }
        }
        minSdk = 24
        withHostTest {}
    }

    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            api(libs.compose.foundation)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        pom {
            name.set("Scripta Editor")
            description.set("Self-drawn virtualized code editor library for Compose Multiplatform.")
            url.set("https://github.com/Ujhhgtg/scripta")
            licenses {
                license {
                    name.set("Apache License, Version 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
        }
    }
    repositories {
        maven {
            name = "ujhhgtgMvn"
            url = uri("https://mvn.pkgs.ujhhgtg.dev/")
            credentials {
                username = providers.gradleProperty("ujhhgtgMvnUsername")
                    .orElse(providers.environmentVariable("UJHHGTG_MVN_USERNAME"))
                    .orNull
                password = providers.gradleProperty("ujhhgtgMvnPassword")
                    .orElse(providers.environmentVariable("UJHHGTG_MVN_PASSWORD"))
                    .orNull
            }
        }
    }
}
