package com.compose.todolist.presentation.theme

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.todolist.data.ThemeOption
import com.compose.todolist.ui.theme.LocalCustomColors

@Composable
fun ThemeSelector(
    title: String,
    themes: List<ThemeOption>,
    selectedThemeId: String,
    onThemeSelected: (ThemeOption) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val customColors = LocalCustomColors.current
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = customColors.headerText,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {

            Spacer(modifier = Modifier.width(8.dp))

            themes.forEach { theme ->
                ThemeThumbnail(
                    theme = theme,
                    isSelected = theme.id == selectedThemeId,
                    onClick = { onThemeSelected(theme) }
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}