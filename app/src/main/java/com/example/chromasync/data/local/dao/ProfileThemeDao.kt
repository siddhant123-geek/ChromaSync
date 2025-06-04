package com.example.chromasync.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.chromasync.data.models.ThemeProfile

@Dao
interface ThemeProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTheme(theme: ThemeProfile)

    @Query("SELECT * FROM ThemeProfile ORDER BY lastModified DESC")
    suspend fun getAllThemes(): List<ThemeProfile>

    @Query("DELETE FROM themeprofile WHERE id = :id")
    suspend fun deleteTheme(id: String)

    @Query("UPDATE themeprofile SET isActive = 0")
    suspend fun markOthersInactive()


    @Query("UPDATE themeprofile SET isActive = 1 WHERE id = :id")
    suspend fun updateTheme(id: String)

    @Transaction
    suspend fun markOthersInactiveAndCurrAsActive(id: String) {
        markOthersInactive()
        updateTheme(id)
    }
}