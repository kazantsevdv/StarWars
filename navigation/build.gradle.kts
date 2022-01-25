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

    implementation(Libs.Hilt.hilt_android)
    kapt(Libs.Hilt.hilt_compiler)
    implementation(Libs.Core.coreKtx)

    implementation(Libs.Navigation.navigationFragment)
    implementation(Libs.Navigation.navigationUi)
}
