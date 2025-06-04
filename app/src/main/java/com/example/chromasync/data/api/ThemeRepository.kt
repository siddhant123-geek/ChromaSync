package com.example.chromasync.data.api

import androidx.room.Query
import com.example.chromasync.data.local.ChromaSyncDatabase
import com.example.chromasync.data.models.ThemeProfile
import com.example.chromasync.utils.ThemeManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

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

}