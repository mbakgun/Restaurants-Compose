import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinKapt)
    id(Plugins.androidHilt)
}

android {
    compileSdk = Configs.compileSdk
    defaultConfig {
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk
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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":restaurants"))

    implementation(Deps.hilt)

    kapt(Deps.hiltCompiler)
    kapt(Deps.hiltAndroidXCompiler)

    testImplementation(Deps.junit)
    testImplementation(Deps.assertj)
    testImplementation(Deps.mockitoCore)
    testImplementation(Deps.coroutineTest)
    testImplementation(Deps.archCoreTesting)
    testImplementation(Deps.mockitoInline)
}
