package com.example.chromasync.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chromasync.data.api.ThemeRepository
import com.example.chromasync.data.models.ThemeProfile
import com.example.chromasync.utils.ThemeManager
import com.example.chromasync.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ThemeViewModel @Inject constructor(val repo: ThemeRepository): ViewModel(){

    private val _currTheme: MutableStateFlow<ThemeProfile> = MutableStateFlow(ThemeManager.getDefaultTheme())
    val currTheme: StateFlow<ThemeProfile> = _currTheme

    private val _currThemes: MutableStateFlow<UiState<List<ThemeProfile>>> =
    MutableStateFlow(UiState.Success(emptyList()))
    val currThemes: StateFlow<UiState<List<ThemeProfile>>> =_currThemes

    init {
        observeDbThemes()
    }

    fun updateAppTheme(newTheme: ThemeProfile) {
        _currTheme.value=newTheme
    }

    fun addThemeToDb(theme: ThemeProfile) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addThemeToDb(theme)

            observeDbThemes()
        }
    }

    fun observeDbThemes() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllThemes().collect {
                _currThemes.value=UiState.Success(it)
            }
        }
    }

    fun refreshThemes() {
        observeDbThemes()
    }

    fun deleteTheme(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.deleteTheme(id)
                refreshThemes()
            }
            catch (e: Exception) {
                Log.d("###", "deleteTheme: error in deleting the theme with " + id)
            }
        }
    }


}