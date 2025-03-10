package com.compose.todolist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.compose.todolist.data.models.SettingsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {
    companion object {
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val API_KEY = stringPreferencesKey("api_key")
        private val CURRENT_THEME_ID = stringPreferencesKey("current_theme_id")
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
            apiKey = preferences[API_KEY] ?: "K-S6uQQHiqRIVtUbXabAcKRUzppF5YfrfioYhy70W8Y",
            currentThemeId = preferences[CURRENT_THEME_ID] ?: "bwr",
            appBackgroundColor = preferences[APP_BACKGROUND_COLOR] ?: "#D9D9D9",
            headerTextColor = preferences[HEADER_TEXT_COLOR] ?: "#303030",
            iconTintColor = preferences[ICON_TINT_COLOR] ?: "#303030",
            cardBackgroundColor = preferences[CARD_BACKGROUND_COLOR] ?: "#C8C8C8",
            cardTextColor = preferences[CARD_TEXT_COLOR] ?: "#303030",
            checkboxBackgroundColor = preferences[CHECKBOX_BACKGROUND_COLOR] ?: "#303030",
            checkboxIconColor = preferences[CHECKBOX_ICON_COLOR] ?: "#D9D9D9",
            fabBackgroundColor = preferences[FAB_BACKGROUND_COLOR] ?: "#D71921",
            fabIconColor = preferences[FAB_ICON_COLOR] ?: "#D9D9D9",
            counterBackgroundColor = preferences[COUNTER_BACKGROUND_COLOR] ?: "#EFEFEF",
            counterTextColor = preferences[COUNTER_TEXT_COLOR] ?: "#303030"
        )
    }

    suspend fun updateApiKey(apiKey: String) {
        context.dataStore.edit { preferences ->
            preferences[API_KEY] = apiKey
        }
    }

    suspend fun applyTheme(theme: ThemeOption) {
        context.dataStore.edit { preferences ->
            preferences[CURRENT_THEME_ID] = theme.id
            preferences[DARK_MODE] = theme.isDark

            theme.colors.forEach { (key, value) ->
                when (key) {
                    "appBackground" -> preferences[APP_BACKGROUND_COLOR] = value
                    "headerText" -> preferences[HEADER_TEXT_COLOR] = value
                    "iconTint" -> preferences[ICON_TINT_COLOR] = value
                    "cardBackground" -> preferences[CARD_BACKGROUND_COLOR] = value
                    "cardText" -> preferences[CARD_TEXT_COLOR] = value
                    "checkboxBackground" -> preferences[CHECKBOX_BACKGROUND_COLOR] = value
                    "checkboxIcon" -> preferences[CHECKBOX_ICON_COLOR] = value
                    "fabBackground" -> preferences[FAB_BACKGROUND_COLOR] = value
                    "fabIcon" -> preferences[FAB_ICON_COLOR] = value
                    "counterBackground" -> preferences[COUNTER_BACKGROUND_COLOR] = value
                    "counterText" -> preferences[COUNTER_TEXT_COLOR] = value
                }
            }
        }
    }
}