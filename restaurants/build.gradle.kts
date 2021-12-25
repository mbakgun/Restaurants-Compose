import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinKapt)
    id(Plugins.androidHilt)
    id(Plugins.kotlinParcelize)
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
    implementation(project(":core"))

    implementation(Deps.kotlinStdLib)
    implementation(Deps.kotlinSerialization)
    implementation(Deps.appCompat)
    implementation(Deps.hilt)
    implementation(Deps.hiltViewModel)
    implementation(Deps.timber)
    implementation(Deps.coroutinesAndroid)
    implementation(Deps.lifecycleRuntime)
    implementation(Deps.compose)
    implementation(Deps.composeCompiler)
    implementation(Deps.composeTooling)
    implementation(Deps.composeFoundation)
    implementation(Deps.composeMaterial)
    implementation(Deps.composeActivity)
    implementation(Deps.composeHiltNavigationViewModel)
    implementation(Deps.composeIcons)
    implementation(Deps.accompanistSwipeRefresh)
    implementation(Deps.accompanistInsetsUi)

    kapt(Deps.hiltCompiler)
    kapt(Deps.hiltAndroidXCompiler)
}
