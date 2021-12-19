import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("io.gitlab.arturbosch.detekt").version(Versions.detektVersion)
    id("com.github.ben-manes.versions").version(Versions.benManesVersion)
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.mbakgun"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0.0"
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

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        this.jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":restaurants"))

    implementation(Deps.appCompat)
    implementation(Deps.hilt)
    implementation(Deps.accompanistInsetsUi)
    implementation(Deps.timber)

    kapt(Deps.hiltCompiler)
    kapt(Deps.hiltAndroidXCompiler)

    debugImplementation(Deps.leakCanary)
}
