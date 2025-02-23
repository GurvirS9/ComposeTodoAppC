package com.compose.todolist.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.compose.todolist.data.SettingsData
import com.compose.todolist.data.SettingsManager
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

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

    fun updateDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            Log.d("SettingsViewModel", "Updating dark mode: $enabled")
            settingsManager.updateDarkMode(enabled)
        }
    }

    fun updateApiKey(apiKey: String) {
        viewModelScope.launch {
            Log.d("SettingsViewModel", "Updating API key: $apiKey")
            settingsManager.updateApiKey(apiKey)
        }
    }

    fun updateColor(key: String, color: String) {
        viewModelScope.launch {
            Log.d("SettingsViewModel", "Updating color: $key -> $color")
            settingsManager.updateColor(key, color)
        }
    }

    fun updateCustomColors(enabled: Boolean) {
        viewModelScope.launch {
            Log.d("SettingsViewModel", "Updating custom colors: $enabled")
            settingsManager.updateCustomColors(enabled)
        }
    }
}