package com.compose.todolist.presentation.settings

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.compose.todolist.data.models.SettingsData
import com.compose.todolist.data.SettingsManager
import com.compose.todolist.data.ThemeOption
import com.compose.todolist.data.ThemeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsManager = SettingsManager(application)
    private val _settings = MutableStateFlow(SettingsData())
    val settings: StateFlow<SettingsData> = _settings

    init {
        viewModelScope.launch {
            settingsManager.settingsData.collectLatest {
                Log.d("SettingsViewModel", "Settings updated: $it")
                _settings.value = it
            }
        }
    }

    fun updateApiKey(apiKey: String) {
        viewModelScope.launch {
            Log.d("SettingsViewModel", "Updating API key: $apiKey")
            settingsManager.updateApiKey(apiKey)
        }
    }

    fun applyTheme(theme: ThemeOption) {
        viewModelScope.launch {
            Log.d("SettingsViewModel", "Applying theme: ${theme.name}")
            settingsManager.applyTheme(theme)
        }
    }

    fun getLightThemes() = ThemeRepo.lightThemes
    fun getDarkThemes() = ThemeRepo.darkThemes
}