package com.example.chromasync.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chromasync.data.local.dao.ThemeProfileDao
import com.example.chromasync.data.models.ThemeProfile

@Database(entities = [ThemeProfile::class], version = 1, exportSchema = false)
abstract class ChromaSyncDatabase: RoomDatabase() {

    abstract fun dao(): ThemeProfileDao


}