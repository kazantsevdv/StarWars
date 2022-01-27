package com.kazantsev.data.di

import android.app.Application
import androidx.room.RoomDatabase
import com.kazantsev.data.database.AppDatabase
import com.kazantsev.data.network.api.ApiDataSource
import com.kazantsev.data.repository.PersonPagingSource
import com.kazantsev.data.repository.RepositoryImpl
import com.kazantsev.data.util.AppCoroutineDispatchers
import com.kazantsev.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindsRepository(repository: RepositoryImpl): Repository
}