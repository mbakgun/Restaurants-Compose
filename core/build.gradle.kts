import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.androidHilt)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinKapt)
    kotlin(Plugins.kotlinSerialization) version Versions.kotlin
}

android {
    compileSdk = Configs.compileSdk
    defaultConfig {
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }

    kapt {
        correctErrorTypes = true
    }

    tasks.withType(KotlinCompile::class).all {
        kotlinOptions {
            jvmTarget = "11"
            allWarningsAsErrors = true
            freeCompilerArgs = listOf(
                "-Xopt-in=kotlin.RequiresOptIn"
            )
        }
    }

    buildFeatures {
        compose = true
        composeOptions.kotlinCompilerExtensionVersion = Versions.composeCompilerVersion
    }
}

dependencies {
    implementation(Deps.kotlinSerialization)
    implementation(Deps.hilt)
    implementation(Deps.compose)
    implementation(Deps.composeCompiler)
    implementation(Deps.composeMaterial)
    implementation(Deps.timber)

    kapt(Deps.hiltCompiler)
    kapt(Deps.hiltAndroidXCompiler)
}
