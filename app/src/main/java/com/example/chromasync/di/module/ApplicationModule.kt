package com.example.chromasync.di.module

import android.content.Context
import androidx.room.Room
import com.example.chromasync.data.api.SyncProfileService
import com.example.chromasync.data.local.ChromaSyncDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideSyncProfileService(): SyncProfileService {
        return SyncProfileService()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ChromaSyncDatabase {
        return Room.databaseBuilder(context, ChromaSyncDatabase::class.java, "ChromaSync").build()
    }

}