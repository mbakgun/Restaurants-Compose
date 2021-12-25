import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.androidHilt)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinKapt)
}

android {
    compileSdk = Configs.compileSdk
    defaultConfig {
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk
        testInstrumentationRunner = Configs.testInstrumentationRunner
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
