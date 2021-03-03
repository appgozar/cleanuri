package com.example.test.di

import android.content.Context
import com.example.test.data.AppDb
import com.example.test.data.LinkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDb{
        return AppDb.getInstance(context)
    }

    @Provides
    fun provideLinkDao(appDb: AppDb) : LinkDao{
        return appDb.linkDao()
    }

}