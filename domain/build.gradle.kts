import com.kazantsev.buildsrc.*
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
    }
}
dependencies {
    // Network
    implementation(Libs.Paging.core)
    implementation(Libs.Coroutines.kotlinCoroutinesAndroid)
    implementation(Libs.Hilt.hilt_android)
    kapt(Libs.Hilt.hilt_compiler)

}
