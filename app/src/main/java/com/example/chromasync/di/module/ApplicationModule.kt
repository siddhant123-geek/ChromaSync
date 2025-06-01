package com.example.chromasync.di.module

import com.example.chromasync.data.api.SyncProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideSyncProfileService(): SyncProfileService {
        return SyncProfileService
    }

}