package com.example.chromasync

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChromaSyncApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}