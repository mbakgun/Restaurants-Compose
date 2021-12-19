object Deps {

    //Build
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

    //App
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerialization}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltAndroidX}"
    const val hiltAndroidXCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntime}"
    const val compose = "androidx.compose.ui:ui:${Versions.composeVersion}"
    const val composeCompiler = "androidx.compose.compiler:compiler:${Versions.composeCompilerVersion}"
    const val composeTooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"
    const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.composeVersion}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.composeVersion}"
    const val composeHiltNavigationViewModel = "androidx.hilt:hilt-navigation-compose:${Versions.composeHiltNavigationViewModel}"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.composeActivityVersion}"
    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.composeNavigationVersion}"
    const val composeIcons = "androidx.compose.material:material-icons-extended:${Versions.composeVersion}"
    const val accompanistSwipeRefresh = "com.google.accompanist:accompanist-swiperefresh:${Versions.accompanistVersion}"
    const val accompanistInsetsUi = "com.google.accompanist:accompanist-insets-ui:${Versions.accompanistVersion}"

    //Test
    const val junit = "junit:junit:${Versions.junit}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockito}"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineTest}"
    const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCoreTesting}"
    const val assertj = "org.assertj:assertj-core:${Versions.assertj}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val testRules = "androidx.test:rules:${Versions.testRules}"
    const val junitExt = "androidx.test.ext:junit-ktx:${Versions.junitExtVersion}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val composeTesting = "androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}"
    const val composeManifestTesting = "androidx.compose.ui:ui-test-manifest:${Versions.composeVersion}"

    //Debug
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanaryVersion}"
}
