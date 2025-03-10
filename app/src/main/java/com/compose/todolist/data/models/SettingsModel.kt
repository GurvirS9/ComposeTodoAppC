package com.compose.todolist.data.models

data class SettingsData(
    val isDarkMode: Boolean = false,
    val apiKey: String = "K-S6uQQHiqRIVtUbXabAcKRUzppF5YfrfioYhy70W8Y",
    val currentThemeId: String = "bwr",
    val appBackgroundColor: String = "#D9D9D9",
    val headerTextColor: String = "#303030",
    val iconTintColor: String = "#303030",
    val cardBackgroundColor: String = "#C8C8C8",
    val cardTextColor: String = "#303030",
    val checkboxBackgroundColor: String = "#303030",
    val checkboxIconColor: String = "#D9D9D9",
    val fabBackgroundColor: String = "#D71921",
    val fabIconColor: String = "#D9D9D9",
    val counterBackgroundColor: String = "#EFEFEF",
    val counterTextColor: String = "#303030"
)
