package com.compose.todolist.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.compose.todolist.presentation.theme.ThemeSelector
import com.compose.todolist.ui.theme.LocalCustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel
) {
    val settings by viewModel.settings.collectAsState()
    var tempApiKey by remember { mutableStateOf(settings.apiKey) }
    var showApiKeyConfirmation by remember { mutableStateOf(false) } // snackbar for api key reporting
    val customColors = LocalCustomColors.current

    Scaffold(
        containerColor = customColors.appBackground,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TopAppBar(
                    title = {
                        Text(
                            "SETTINGS",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(start = 16.dp, top = 30.dp),
                            color = customColors.headerText
                        )
                    },
                    actions = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = "Home",
                                tint = customColors.iconTint
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = customColors.appBackground,
                        titleContentColor = customColors.headerText,
                        actionIconContentColor = customColors.iconTint
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        snackbarHost = {
            if (showApiKeyConfirmation) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = { showApiKeyConfirmation = false }) {
                            Text("Dismiss")
                        }
                    }
                ) {
                    Text("API Key updated successfully!")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = "Theme",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = customColors.headerText,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ThemeSelector(
                title = "Light Mode:",
                themes = viewModel.getLightThemes(),
                selectedThemeId = settings.currentThemeId,
                onThemeSelected = { viewModel.applyTheme(it) }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            ThemeSelector(
                title = "Dark Mode:",
                themes = viewModel.getDarkThemes(),
                selectedThemeId = settings.currentThemeId,
                onThemeSelected = { viewModel.applyTheme(it) }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "Enter your API key:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = customColors.headerText
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = tempApiKey,
                    onValueChange = { tempApiKey = it },
                    label = { Text("API Key") },
                    modifier = Modifier.padding(top = 14.dp).weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = customColors.headerText,
                        unfocusedTextColor = customColors.headerText,
                        focusedBorderColor = customColors.fabBackground,
                        unfocusedBorderColor = customColors.headerText,
                        cursorColor = customColors.fabBackground,
                        focusedLabelColor = customColors.fabBackground,
                        unfocusedLabelColor = customColors.cardText,
                        selectionColors = TextSelectionColors(
                            handleColor = customColors.fabBackground,
                            backgroundColor = customColors.fabBackground
                        )
                    )
                )
                IconButton(
                    onClick = {
                        viewModel.updateApiKey(tempApiKey)
                        showApiKeyConfirmation = true
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Confirm API Key",
                        tint = customColors.fabBackground,
                        modifier = Modifier.padding(top = 12.dp).size(size = 24.dp)
                    )
                }
            }
        }
    }
}
