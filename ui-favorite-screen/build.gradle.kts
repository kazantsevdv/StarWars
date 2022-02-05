import com.kazantsev.buildsrc.AppConfig
import com.kazantsev.buildsrc.Libs



plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = AppConfig.compileSdk
    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
    }
    buildFeatures {
        viewBinding = true
    }
}
dependencies {
    implementation(project(":domain"))
    implementation(project(":navigation"))
    implementation(project(":ui-common"))
    implementation(Libs.Hilt.hilt_android)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt(Libs.Hilt.hilt_compiler)
    implementation(Libs.Core.coreKtx)
    implementation(Libs.Core.appCompat)
    implementation(Libs.Core.material)
    implementation(Libs.Core.supportFragment)
    implementation(Libs.Core.supportLifecycle)
    implementation(Libs.Coroutines.kotlinCoroutinesAndroid)
    implementation(Libs.Navigation.navigationFragment)
    implementation(Libs.Navigation.navigationUi)
}
