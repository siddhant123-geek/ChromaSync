package com.example.chromasync.data.api

import com.example.chromasync.data.models.ThemeProfile
import kotlinx.coroutines.delay
import kotlin.random.Random

object SyncProfileService {

    suspend fun syncThemeProfile(themeProfile: ThemeProfile) {
        delay(500)


        // sync api calls
        if (Random.nextBoolean()) {
            throw Exception("Simulated network error")
        }
    }
}