package com.example.chromasync.data.api

import android.content.SyncResult
import android.util.Log
import androidx.room.Query
import com.example.chromasync.data.local.ChromaSyncDatabase
import com.example.chromasync.data.models.ThemeProfile
import com.example.chromasync.utils.SyncState
import com.example.chromasync.utils.ThemeManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class ThemeRepository @Inject constructor(val networkService: SyncProfileService,
                                          val db: ChromaSyncDatabase) {

    suspend fun addThemeToDb(newTheme: ThemeProfile) {
        db.dao().insertTheme(newTheme)
    }

    suspend fun getAllThemes(): Flow<List<ThemeProfile>> {
        return flow {
            emit(db.dao().getAllThemes())
        }
    }

    suspend fun deleteTheme(id: String) {
        db.dao().deleteTheme(id)
    }

    suspend fun updateActiveTheme(id: String) {
        db.dao().markOthersInactiveAndCurrAsActive(id)
    }

    suspend fun syncThemes(callback: (Int)-> Unit): Flow<SyncState> {
        return flow {
            emit(SyncState.Syncing)
            try {
                val allThemes = db.dao().getAllThemes()
                Log.d("###", "syncThemes: after delay in repo")
                networkService.syncThemeProfiles(allThemes) {
                    Log.d("###", "syncThemes: success in syncing")
                    callback.invoke(it)
                }
                Log.d("###", "syncThemes: emitting success")
                emit(SyncState.SyncSuccess)
            } catch (e: Exception) {
                Log.d("###", "syncThemes: error in syncing")
                emit(SyncState.SyncError)
            }
        }
    }
}