package com.example.chromasync.data.api

import com.example.chromasync.data.models.ThemeProfile
import com.example.chromasync.utils.ThemeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeRepository @Inject constructor(networkService: SyncProfileService) {

    private val _currTheme: MutableStateFlow<ThemeProfile> = MutableStateFlow(ThemeManager.getDefaultThemeManager())
    val currTheme: StateFlow<ThemeProfile> = _currTheme

    fun appTheme(newTheme: ThemeProfile) {
        _currTheme.value=newTheme
    }
}