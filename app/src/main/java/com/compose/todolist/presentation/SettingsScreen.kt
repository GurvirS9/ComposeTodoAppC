package com.compose.todolist.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.compose.todolist.ui.theme.LocalCustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel
) {
    val settings by viewModel.settings.collectAsState()
    var showErrorSnackbar by remember { mutableStateOf(false) }
    var tempApiKey by remember { mutableStateOf(settings.apiKey) }
    var showApiKeyConfirmation by remember { mutableStateOf(false) }
    val customColors = LocalCustomColors.current

    Scaffold(
        topBar = {
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
        },
        snackbarHost = {
            if (showErrorSnackbar) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = { showErrorSnackbar = false }) {
                            Text("Dismiss")
                        }
                    }
                ) {
                    Text("Invalid hex color code! Format: #RRGGBB or #RGB")
                }
            }
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
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Force Night Mode",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "Enable Dark Mode overwriting system setting",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Switch(
                    checked = settings.isDarkMode,
                    onCheckedChange = { viewModel.updateDarkMode(it) }
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "Enter your API key:",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = tempApiKey,
                    onValueChange = { tempApiKey = it },
                    label = { Text("API Key") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
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
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Custom Colors (Experimental)",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "Enable fancy custom color scheme :O",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Switch(
                    checked = settings.useCustomColors,
                    onCheckedChange = { viewModel.updateCustomColors(it) },
                    enabled = !settings.isDarkMode
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            AnimatedVisibility(
                visible = settings.useCustomColors && !settings.isDarkMode,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Input HEX values (#000000 - #FFFFFF, #RRGGBB)",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    ColorSettingRow(
                        label = "App Background",
                        currentColor = settings.appBackgroundColor,
                        onValidColor = { viewModel.updateColor("appBackground", it) },
                        onInvalidColor = { showErrorSnackbar = true }
                    )
                    ColorSettingRow(
                        label = "Header Text",
                        currentColor = settings.headerTextColor,
                        onValidColor = { viewModel.updateColor("headerText", it) },
                        onInvalidColor = { showErrorSnackbar = true }
                    )
                    ColorSettingRow(
                        label = "Icons",
                        currentColor = settings.iconTintColor,
                        onValidColor = { viewModel.updateColor("iconTint", it) },
                        onInvalidColor = { showErrorSnackbar = true }
                    )
                    ColorSettingRow(
                        label = "Card Background",
                        currentColor = settings.cardBackgroundColor,
                        onValidColor = { viewModel.updateColor("cardBackground", it) },
                        onInvalidColor = { showErrorSnackbar = true }
                    )
                    ColorSettingRow(
                        label = "Card Text",
                        currentColor = settings.cardTextColor,
                        onValidColor = { viewModel.updateColor("cardText", it) },
                        onInvalidColor = { showErrorSnackbar = true }
                    )
                    ColorSettingRow(
                        label = "Checkbox Background",
                        currentColor = settings.checkboxBackgroundColor,
                        onValidColor = { viewModel.updateColor("checkboxBackground", it) },
                        onInvalidColor = { showErrorSnackbar = true }
                    )
                    ColorSettingRow(
                        label = "Checkbox Icon",
                        currentColor = settings.checkboxIconColor,
                        onValidColor = { viewModel.updateColor("checkboxIcon", it) },
                        onInvalidColor = { showErrorSnackbar = true }
                    )
                    ColorSettingRow(
                        label = "Add Button Background",
                        currentColor = settings.fabBackgroundColor,
                        onValidColor = { viewModel.updateColor("fabBackground", it) },
                        onInvalidColor = { showErrorSnackbar = true }
                    )
                    ColorSettingRow(
                        label = "Add Button Icon",
                        currentColor = settings.fabIconColor,
                        onValidColor = { viewModel.updateColor("fabIcon", it) },
                        onInvalidColor = { showErrorSnackbar = true }
                    )
                    ColorSettingRow(
                        label = "Counter Background",
                        currentColor = settings.counterBackgroundColor,
                        onValidColor = { viewModel.updateColor("counterBackground", it) },
                        onInvalidColor = { showErrorSnackbar = true }
                    )
                    ColorSettingRow(
                        label = "Counter Text",
                        currentColor = settings.counterTextColor,
                        onValidColor = { viewModel.updateColor("counterText", it) },
                        onInvalidColor = { showErrorSnackbar = true }
                    )
                }
            }
        }
    }
}

@Composable
fun ColorSettingRow(
    label: String,
    currentColor: String,
    onValidColor: (String) -> Unit,
    onInvalidColor: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var tempColor by remember { mutableStateOf(currentColor) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        OutlinedTextField(
            value = currentColor,
            onValueChange = { },
            modifier = Modifier.width(120.dp),
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit color",
                    modifier = Modifier.clickable { showDialog = true }
                )
            }
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                tempColor = currentColor
            },
            title = { Text("Edit Color") },
            text = {
                OutlinedTextField(
                    value = tempColor,
                    onValueChange = { tempColor = it.uppercase() },
                    singleLine = true,
                    placeholder = { Text("#FFFFFF") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (isValidHexColor(tempColor)) {
                            onValidColor(tempColor)
                            showDialog = false
                        } else {
                            onInvalidColor()
                        }
                    }
                ) {
                    Text("Done")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        tempColor = currentColor
                        showDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

private fun isValidHexColor(color: String): Boolean {
    if (color.length == 7 && color[0] == '#') {
        for (i in 1 until color.length) {
            val c = color[i]
            if (!c.isDigit() && (c !in 'a'..'f') && (c !in 'A'..'F')) {
                return false
            }
        }
        return true
    }
    return false
}