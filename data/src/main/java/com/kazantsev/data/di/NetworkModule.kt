package com.kazantsev.data.di

import android.widget.ImageView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kazantsev.data.BuildConfig
import com.kazantsev.data.image.ImageLoader
import com.kazantsev.data.image.ImageLoaderImpl
import com.kazantsev.data.network.api.ApiDataSource
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Named("baseUrl")
    @Provides
    fun baseUrl() = "https://swapi.dev/api/"

    @Singleton
    @Provides
    fun api(
        @Named("baseUrl") baseUrl: String,
        gson: Gson,
        moshi: Moshi,
        client: OkHttpClient,
    ): ApiDataSource =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
             .addConverterFactory(GsonConverterFactory.create(gson))
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiDataSource::class.java)

    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder()
        .create()

    @Singleton
    @Provides
    fun createMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun client(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .addInterceptor(HeaderInterceptor())
            .build()

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
            return chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideImageLoader(): ImageLoader<ImageView> =
        ImageLoaderImpl()
}
