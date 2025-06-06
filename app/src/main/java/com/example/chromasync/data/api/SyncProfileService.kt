package com.example.chromasync.data.api

import android.content.SyncResult
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.chromasync.data.models.ThemeProfile
import kotlinx.coroutines.delay
import kotlin.random.Random

class SyncProfileService() {

    // simulating a server with a lit of stored themes
    // and a last synced time
    private val apiThemes: MutableList<ThemeProfile> = mutableListOf()
    private var lastSynced: Long = 0L

    suspend fun syncThemeProfiles(themes: List<ThemeProfile>, succCallback: (Int)-> Unit) {
        delay(2000)
        if (Random.nextBoolean()) {
            Log.d("###", "syncThemeProfiles: error in sync throwing error")
            throw Exception("Simulated network error")
        }
        // sync api calls
        resolveConflictIfAnyAndSync(themes, succCallback)
    }

    private suspend fun resolveConflictIfAnyAndSync(dbThemes: List<ThemeProfile>, callback:(Int)-> Unit) {
        Log.d("###", "resolveConflictIfAnyAndSync: coming to this")
        Log.d("###", "resolveConflictIfAnyAndSync: coming to this dbTheme Size " + dbThemes.size)
        var syncItems=0
        // creating a hashmap for API themes with name as key
        // and the value as the stored ThemeProfile
        val apiThemeMap = HashMap<String, ThemeProfile>()
        apiThemes.forEach { theme ->
            apiThemeMap[theme.name] = theme
        }
        dbThemes.forEach { dbTheme ->
            Log.d("###", "resolveConflictIfAnyAndSync: coming inside dbThemes check")
            if (dbTheme.lastModified > lastSynced) {
                // Check if theme exists in API map
                if (apiThemeMap.contains(dbTheme.name)) {
                    val apiTheme = apiThemeMap[dbTheme.name]!!

                    // compare last modified and take the newer one
                    if (dbTheme.lastModified > apiTheme.lastModified) {
                        // DB theme is newer -
                        // replace the API theme with the db theme
                        val index = apiThemes.indexOfFirst { it.name == dbTheme.name }
                        if (index != -1) {
                            apiThemes[index] = dbTheme
                        }
                        syncItems++
                    }
                    // If API theme is newer take the apitheme
                } else {
                    // it is a new theme altogether
                    apiThemes.add(dbTheme)
                    syncItems++
                }
            }
        }

//        lastSynced=System.currentTimeMillis()

        Log.d("###", "resolveConflictIfAnyAndSync: syncedItems " + syncItems)
        callback.invoke(syncItems)
    }
}