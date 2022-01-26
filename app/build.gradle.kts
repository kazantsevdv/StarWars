import com.kazantsev.buildsrc.AppConfig
import com.kazantsev.buildsrc.Libs

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}
kapt {
    correctErrorTypes = true
}
android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile(AppConfig.proguardOptimize),
                AppConfig.proguardRulesPro
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }

}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":navigation"))
    implementation(project(":ui-search-screen"))
    implementation(project(":ui-detail-screen"))
    implementation(project(":ui-favorite-screen"))

    implementation(Libs.Core.coreKtx)
    implementation(Libs.Core.appCompat)
    implementation(Libs.Core.material)
    implementation(Libs.Hilt.hilt_android)
    implementation(Libs.Coroutines.kotlinCoroutinesAndroid)
    implementation(Libs.Navigation.navigationFragment)
    implementation(Libs.Navigation.navigationUi)
    kapt(Libs.Hilt.hilt_compiler)
}
