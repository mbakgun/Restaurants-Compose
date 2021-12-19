import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31
    defaultConfig {
        minSdk = 21
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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

    testOptions {
        animationsDisabled = true
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":restaurants"))

    implementation(Deps.hilt)
    implementation(Deps.composeHiltNavigationViewModel)
    implementation(Deps.composeMaterial)

    kapt(Deps.hiltCompiler)
    kapt(Deps.hiltAndroidXCompiler)

    androidTestImplementation(Deps.testRules)
    androidTestImplementation(Deps.espresso)
    androidTestImplementation(Deps.junitExt)
    androidTestImplementation(Deps.espressoContrib)
    androidTestImplementation(Deps.composeTesting)

    debugImplementation(Deps.composeManifestTesting)
}
