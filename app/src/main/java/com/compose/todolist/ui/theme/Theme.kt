package com.compose.todolist.ui.theme

import android.app.Activity
import android.graphics.Color as AndroidColor
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.compose.todolist.data.SettingsData

private val LightColors = lightColorScheme(
    primary = Color(0xFF53A4FF),
    secondary = Color(0xFF03DAC6),
    tertiary = Color(0xFF3700B3),
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF0D42FC),
    secondary = Color(0xFF03DAC6),
    tertiary = Color(0xFF3700B3),
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun TodoTheme(
    settings: SettingsData,
    content: @Composable () -> Unit
) {
    val darkTheme = settings.isDarkMode || isSystemInDarkTheme()
    val colorScheme = if (darkTheme) DarkColors else LightColors

    // Only use custom colors if they're enabled AND we're not in dark mode
    val customColors = if (settings.useCustomColors && !darkTheme) {
        createCustomColors(settings)
    } else {
        CustomColors(
            appBackground = if (darkTheme) Color(0xFF121212) else Color.White,
            headerText = if (darkTheme) Color.White else Color.Black,
            iconTint = if (darkTheme) Color.White else Color.Black,
            cardBackground = if (darkTheme) Color(0xFF2D2D2D) else Color(0xFFF3F3F3),
            cardText = if (darkTheme) Color.White else Color.Black,
            checkboxBackground = if (darkTheme) Color(0xFF0047B1) else Color.Black,
            checkboxIcon = if (darkTheme) Color.White else Color.Black,
            fabBackground = if (darkTheme) Color(0xFF0D42FC) else Color(0xFF53A4FF),
            fabIcon = if (darkTheme) Color.Black else Color.White,
            counterBackground = if (darkTheme) Color(0xFF2D2D2D) else Color.White,
            counterText = if (darkTheme) Color.White else Color(0xFF666666)
        )
    }

    Log.d("TodoTheme", "Dark theme: $darkTheme")
    Log.d("TodoTheme", "Custom colors enabled: ${settings.useCustomColors}")
    Log.d("TodoTheme", "Custom colors applied: $customColors")
    Log.d("TodoTheme", "Settings received: $settings")

    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val backgroundColor = customColors.appBackground.toArgb()
        window.decorView.setBackgroundColor(backgroundColor)
        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = !darkTheme
            isAppearanceLightNavigationBars = !darkTheme
        }
    }



    MaterialTheme(
        colorScheme = colorScheme,
        content = {
            CompositionLocalProvider(
                LocalCustomColors provides customColors,
                content = content
            )
        }
    )
}

private fun parseColorSafely(colorString: String, fallback: Color): Color {
    return try {
        Color(AndroidColor.parseColor(colorString))
    } catch (e: IllegalArgumentException) {
        Log.e("TodoTheme", "Error parsing color: $colorString", e)
        fallback
    }
}

private fun createCustomColors(settings: SettingsData): CustomColors {
    return CustomColors(
        appBackground = parseColorSafely(settings.appBackgroundColor, Color.White),
        headerText = parseColorSafely(settings.headerTextColor, Color.Black),
        iconTint = parseColorSafely(settings.iconTintColor, Color.Black),
        cardBackground = parseColorSafely(settings.cardBackgroundColor, Color(0xFFB8B8B8)),
        cardText = parseColorSafely(settings.cardTextColor, Color.Black),
        checkboxBackground = parseColorSafely(settings.checkboxBackgroundColor, Color.Black),
        checkboxIcon = parseColorSafely(settings.checkboxIconColor, Color.White),
        fabBackground = parseColorSafely(settings.fabBackgroundColor, Color(0xFFDB1D1D)),
        fabIcon = parseColorSafely(settings.fabIconColor, Color.White),
        counterBackground = parseColorSafely(settings.counterBackgroundColor, Color.White),
        counterText = parseColorSafely(settings.counterTextColor, Color(0xFF666666))
    )
}