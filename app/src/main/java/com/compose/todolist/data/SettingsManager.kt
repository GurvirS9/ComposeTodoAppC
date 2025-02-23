package com.compose.todolist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {
    companion object {
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val USE_CUSTOM_COLORS = booleanPreferencesKey("use_custom_colors")
        private val API_KEY = stringPreferencesKey("api_key")
        private val APP_BACKGROUND_COLOR = stringPreferencesKey("app_background_color")
        private val HEADER_TEXT_COLOR = stringPreferencesKey("header_text_color")
        private val ICON_TINT_COLOR = stringPreferencesKey("icon_tint_color")
        private val CARD_BACKGROUND_COLOR = stringPreferencesKey("card_background_color")
        private val CARD_TEXT_COLOR = stringPreferencesKey("card_text_color")
        private val CHECKBOX_BACKGROUND_COLOR = stringPreferencesKey("checkbox_background_color")
        private val CHECKBOX_ICON_COLOR = stringPreferencesKey("checkbox_icon_color")
        private val FAB_BACKGROUND_COLOR = stringPreferencesKey("fab_background_color")
        private val FAB_ICON_COLOR = stringPreferencesKey("fab_icon_color")
        private val COUNTER_BACKGROUND_COLOR = stringPreferencesKey("counter_background_color")
        private val COUNTER_TEXT_COLOR = stringPreferencesKey("counter_text_color")
    }

    val settingsData: Flow<SettingsData> = context.dataStore.data.map { preferences ->
        SettingsData(
            isDarkMode = preferences[DARK_MODE] ?: false,
            useCustomColors = preferences[USE_CUSTOM_COLORS] ?: false,
            apiKey = preferences[API_KEY] ?: "K-S6uQQHiqRIVtUbXabAcKRUzppF5YfrfioYhy70W8Y",
            appBackgroundColor = preferences[APP_BACKGROUND_COLOR] ?: "#FFFFFF",
            headerTextColor = preferences[HEADER_TEXT_COLOR] ?: "#000000",
            iconTintColor = preferences[ICON_TINT_COLOR] ?: "#000000",
            cardBackgroundColor = preferences[CARD_BACKGROUND_COLOR] ?: "#F3F3F3",
            cardTextColor = preferences[CARD_TEXT_COLOR] ?: "#000000",
            checkboxBackgroundColor = preferences[CHECKBOX_BACKGROUND_COLOR] ?: "#000000",
            checkboxIconColor = preferences[CHECKBOX_ICON_COLOR] ?: "#FFFFFF",
            fabBackgroundColor = preferences[FAB_BACKGROUND_COLOR] ?: "#6200EE",
            fabIconColor = preferences[FAB_ICON_COLOR] ?: "#FFFFFF",
            counterBackgroundColor = preferences[COUNTER_BACKGROUND_COLOR] ?: "#FFFFFF",
            counterTextColor = preferences[COUNTER_TEXT_COLOR] ?: "#666666"
        )
    }

    suspend fun updateColor(key: String, color: String) {
        context.dataStore.edit { preferences ->
            when (key) {
                "appBackground" -> preferences[APP_BACKGROUND_COLOR] = color
                "headerText" -> preferences[HEADER_TEXT_COLOR] = color
                "iconTint" -> preferences[ICON_TINT_COLOR] = color
                "cardBackground" -> preferences[CARD_BACKGROUND_COLOR] = color
                "cardText" -> preferences[CARD_TEXT_COLOR] = color
                "checkboxBackground" -> preferences[CHECKBOX_BACKGROUND_COLOR] = color
                "checkboxIcon" -> preferences[CHECKBOX_ICON_COLOR] = color
                "fabBackground" -> preferences[FAB_BACKGROUND_COLOR] = color
                "fabIcon" -> preferences[FAB_ICON_COLOR] = color
                "counterBackground" -> preferences[COUNTER_BACKGROUND_COLOR] = color
                "counterText" -> preferences[COUNTER_TEXT_COLOR] = color
            }
        }
    }

    suspend fun updateDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE] = enabled
        }
    }

    suspend fun updateApiKey(apiKey: String) {
        context.dataStore.edit { preferences ->
            preferences[API_KEY] = apiKey
        }
    }

    suspend fun updateCustomColors(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USE_CUSTOM_COLORS] = enabled
        }
    }
}