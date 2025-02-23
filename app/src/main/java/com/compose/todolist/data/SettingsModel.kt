package com.compose.todolist.data

data class SettingsData(
    val isDarkMode: Boolean = false,
    val useCustomColors: Boolean = false,
    val apiKey: String = "K-S6uQQHiqRIVtUbXabAcKRUzppF5YfrfioYhy70W8Y",
    val appBackgroundColor: String = "#FFFFFF",
    val headerTextColor: String = "#000000",
    val iconTintColor: String = "#000000",
    val cardBackgroundColor: String = "#F3F3F3",
    val cardTextColor: String = "#000000",
    val checkboxBackgroundColor: String = "#000000",
    val checkboxIconColor: String = "#FFFFFF",
    val fabBackgroundColor: String = "#6200EE",
    val fabIconColor: String = "#FFFFFF",
    val counterBackgroundColor: String = "#FFFFFF",
    val counterTextColor: String = "#666666"
)