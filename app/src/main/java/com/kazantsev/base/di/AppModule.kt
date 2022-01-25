package com.kazantsev.base.di

import android.content.Context
import com.kazantsev.data.di.NetworkModule
import com.kazantsev.data.util.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(
//    includes = [
//        //NetworkModule::class,
//    ]
)
object AppModule {
    @Singleton
    @Provides
    fun provideCoroutineDispatchers() = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )

    @Provides
    @Singleton
    @Named("cache")
    fun provideCacheDir(
        @ApplicationContext context: Context,
    ): File = context.cacheDir

//    @Provides
//    @Named("api")
//    fun provideApiKey(): String = BuildConfig.API_KEY

}