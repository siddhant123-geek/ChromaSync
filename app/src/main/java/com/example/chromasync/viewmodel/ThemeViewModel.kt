package com.example.chromasync.viewmodel

import androidx.lifecycle.ViewModel
import com.example.chromasync.data.api.ThemeRepository
import com.example.chromasync.data.models.ThemeProfile
import com.example.chromasync.utils.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(val repo: ThemeRepository): ViewModel(){

    private val _currTheme: MutableStateFlow<ThemeProfile> = MutableStateFlow(ThemeManager.getDefaultTheme())
    val currTheme: StateFlow<ThemeProfile> = _currTheme

    fun appTheme(newTheme: ThemeProfile) {
        _currTheme.value=newTheme
    }


}