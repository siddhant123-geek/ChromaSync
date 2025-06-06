package com.example.chromasync.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chromasync.data.api.ThemeRepository
import com.example.chromasync.data.models.ThemeProfile
import com.example.chromasync.utils.SyncState
import com.example.chromasync.utils.ThemeManager
import com.example.chromasync.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
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


    private val _syncState: MutableStateFlow<SyncState> = MutableStateFlow(SyncState.NotSynced)
    val syncState: StateFlow<SyncState> =_syncState

    init {
        observeDbThemes()
    }

    fun updateAppTheme(newTheme: ThemeProfile) {
        _currTheme.value=newTheme
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateActiveTheme(newTheme.id)
        }
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
                for(theme in it) {
                    if(theme.isActive) {
                        _currTheme.value=theme
                        break
                    }
                }
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

    fun syncThemes(successCallback: (Int)-> Unit) {
        Log.d("###", "ViewModel: syncThemes called")

        // to prevent repeated sync hits when the curr sync is not completed
        if (_syncState.value == SyncState.Syncing) {
            Log.d("###", "ViewModel: Sync already in progress, ignoring")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            repo.syncThemes(successCallback)
                .catch { exception ->
                    Log.d("###", "ViewModel: Exception in flow - ${exception.message}")
                    _syncState.value = SyncState.SyncError
                }
                .collect { state ->
                    Log.d("###", "ViewModel: Received state - $state")
                    _syncState.value = state
                }
        }
    }

}