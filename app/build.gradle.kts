plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.dokka") version "1.9.0"
}

android {
    namespace = "dev.davidvega.rpgame"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.davidvega.rpgame"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    implementation(libs.fasterxml.jackson.core)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.databind)
    implementation(kotlin("script-runtime"))
}

// Configuración de Dokka
tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("docs")) // Carpeta donde se generará la documentación

    dokkaSourceSets {
        create("main") {
            displayName.set("Android Project Documentation")
            sourceRoots.from(
                file("src/main/java"), // Código Java
            )
            platform.set(org.jetbrains.dokka.Platform.jvm) // Android JVM
            classpath.from(
                files(android.bootClasspath), // Clases base de Android
                configurations["debugCompileClasspath"], // Rutas para dependencias
                configurations["releaseCompileClasspath"] // Rutas de clases en modo release
            )
        }
    }
}

/*

plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.dokka") version "1.9.0"
}

android {
    namespace = "dev.davidvega.rpgame"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.davidvega.rpgame"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }


}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    implementation (libs.fasterxml.jackson.core)
    implementation (libs.jackson.annotations)
    implementation (libs.jackson.databind)
}



 */