package com.example.chromasync.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ThemeProfile(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "primaryColor")
    val primaryColor: String,
    @ColumnInfo(name = "secondaryColor")
    val secondaryColor: String,
    @ColumnInfo(name = "surfaceColor")
    val surfaceColor: String,
    @ColumnInfo(name = "backgroundColor")
    val backgroundColor: String,
    @ColumnInfo(name = "primaryTextColor")
    val primaryTextColor: String,
    @ColumnInfo(name = "secondaryTextColor")
    val secondaryTextColor: String,
    @ColumnInfo(name = "cornerRadius")
    val cornerRadius: Float,
    @ColumnInfo(name = "fontFamily")
    val fontFamily: String,
    @ColumnInfo(name = "isActive")
    val isActive: Boolean = false,
    @ColumnInfo(name = "lastModified")
    val lastModified: Long
)