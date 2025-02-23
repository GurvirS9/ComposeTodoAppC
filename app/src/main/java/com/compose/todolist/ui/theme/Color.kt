package com.compose.todolist.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColors(
    val appBackground: Color,
    val headerText: Color,
    val iconTint: Color,
    val cardBackground: Color,
    val cardText: Color,
    val checkboxBackground: Color,
    val checkboxIcon: Color,
    val fabBackground: Color,
    val fabIcon: Color,
    val counterBackground: Color,
    val counterText: Color
)

val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        appBackground = Color.White,
        headerText = Color.Black,
        iconTint = Color.Black,
        cardBackground = Color(0xFFB8B8B8),
        cardText = Color.Black,
        checkboxBackground = Color.Black,
        checkboxIcon = Color.White,
        fabBackground = Color(0xFFDB1D1D),
        fabIcon = Color.White,
        counterBackground = Color.White,
        counterText = Color(0xFF666666)
    )
}