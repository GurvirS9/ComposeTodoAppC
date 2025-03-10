package com.compose.todolist.presentation.todo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.todolist.data.models.Todo
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import com.compose.todolist.ui.theme.LocalCustomColors
import com.compose.todolist.utils.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    onNavigateToSettings: () -> Unit,
    viewModel: TodoViewModel = viewModel()
) {
    val todos by viewModel.todos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    var showCreateSheet by remember { mutableStateOf(false) }
    var selectedTodo by remember { mutableStateOf<Todo?>(null) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val customColors = LocalCustomColors.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(customColors.appBackground)
    ) {
        Scaffold(
            containerColor = customColors.appBackground,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "TO DO",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(start = 16.dp, top = 30.dp),
                            color = customColors.headerText
                        )
                    },
                    actions = {
                        IconButton(onClick = { viewModel.loadTodos() }) {
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = "Refresh",
                                tint = customColors.iconTint
                            )
                        }
                        IconButton(onClick = onNavigateToSettings) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = "Settings",
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
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showCreateSheet = true },
                    modifier = Modifier.padding(16.dp),
                    containerColor = customColors.fabBackground
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Todo",
                        tint = customColors.fabIcon
                    )
                }
            }
        ) { padding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = customColors.headerText
                    )
                } else if (todos.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No todo(s) found",
                            style = MaterialTheme.typography.bodyLarge,
                            color = customColors.headerText
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(todos) { todo ->
                            TodoItem(
                                todo = todo,
                                onClick = { selectedTodo = todo },
                                onStatusChange = { isCompleted ->
                                    viewModel.updateTodo(
                                        todo.copy(
                                            status = if (isCompleted) "completed" else "pending"
                                        )
                                    )
                                }
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(72.dp))
                        }
                    }
                }

                if (todos.isNotEmpty()) {
                    val pendingTasks = todos.count { it.status == "pending" }
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = customColors.counterBackground,
                        shadowElevation = 4.dp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "$pendingTasks ${if (pendingTasks == 1) "task" else "tasks"} left",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 14.sp,
                                color = customColors.counterText
                            )
                        )
                    }
                }

                error?.let { errorMessage ->
                    Snackbar(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomCenter),
                        action = {
                            TextButton(onClick = { viewModel.clearError() }) {
                                Text("Dismiss", color = customColors.headerText)
                            }
                        },
                        containerColor = customColors.cardBackground,
                        contentColor = customColors.cardText
                    ) {
                        Text(errorMessage)
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showCreateSheet || (selectedTodo != null && !showDeleteConfirmation),
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        onClick = {
                            showCreateSheet = false
                            selectedTodo = null
                        }
                    ),
                color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f)
            ) {}
        }

        AnimatedVisibility(
            visible = showCreateSheet,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = customColors.cardBackground,
                shadowElevation = 8.dp
            ) {
                TodoEditContent(
                    title = "New Task",
                    initialTodo = null,
                    showDelete = false,
                    showStatus = false,
                    onCancel = { showCreateSheet = false },
                    onSave = { title, description, _ ->
                        viewModel.createTodo(
                            title,
                            description
                        )
                        showCreateSheet = false
                    }
                )
            }
        }

        AnimatedVisibility(
            visible = selectedTodo != null && !showDeleteConfirmation,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            selectedTodo?.let { todo ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = customColors.cardBackground,
                    shadowElevation = 8.dp
                ) {
                    TodoEditContent(
                        title = "Update Task",
                        initialTodo = todo,
                        showDelete = true,
                        showStatus = true,
                        onCancel = { selectedTodo = null },
                        onSave = { title, description, isCompleted ->
                            viewModel.updateTodo(
                                todo.copy(
                                    title = title,
                                    description = description,
                                    status = if (isCompleted) "completed" else "pending"
                                )
                            )
                            selectedTodo = null
                        },
                        onDelete = { showDeleteConfirmation = true }
                    )
                }
            }
        }

        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text("Are you sure you want to delete this task?", color = customColors.headerText) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedTodo?.let { viewModel.deleteTodo(it.id) }
                            showDeleteConfirmation = false
                            selectedTodo = null
                        }
                    ) {
                        Text("Confirm", color = customColors.headerText)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteConfirmation = false }) {
                        Text("Cancel", color = customColors.headerText)
                    }
                },
                containerColor = customColors.cardBackground,
                titleContentColor = customColors.cardText,
                textContentColor = customColors.cardText
            )
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onClick: () -> Unit,
    onStatusChange: (Boolean) -> Unit
) {
    val customColors = LocalCustomColors.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = customColors.cardBackground
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(
                checked = todo.status == "completed",
                onCheckedChange = { isChecked -> onStatusChange(isChecked) },
                modifier = Modifier.padding(top = 4.dp),
                colors = CheckboxDefaults.colors(
                    checkedColor = customColors.checkboxBackground,
                    uncheckedColor = customColors.checkboxBackground,
                    checkmarkColor = customColors.checkboxIcon
                )
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        textDecoration = if (todo.status == "completed") {
                            TextDecoration.LineThrough
                        } else {
                            TextDecoration.None
                        },
                        color = customColors.cardText
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = todo.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textDecoration = if (todo.status == "completed") {
                            TextDecoration.LineThrough
                        } else {
                            TextDecoration.None
                        }
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = customColors.cardText
                )
            }
        }
    }
}

@Composable
fun TodoEditContent(
    title: String,
    initialTodo: Todo?,
    showDelete: Boolean = false,
    showStatus: Boolean = false,
    onCancel: () -> Unit,
    onSave: (String, String, Boolean) -> Unit,
    onDelete: (() -> Unit)? = null
) {
    val customColors = LocalCustomColors.current
    var todoTitle by remember { mutableStateOf(initialTodo?.title ?: "") }
    var description by remember { mutableStateOf(initialTodo?.description ?: "") }
    var isCompleted by remember { mutableStateOf(initialTodo?.status == "completed") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = customColors.headerText
            )
            if (showDelete && onDelete != null) {
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFFF0000)
                    )
                }
            }
        }

        if (initialTodo != null) {
            Text(
                "ID: ${initialTodo.id}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 4.dp),
                color = customColors.headerText
            )
            Text(
                "Date: ${DateUtils.convertUTCtoIST(initialTodo.created_at)}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp),
                color = customColors.headerText
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = todoTitle,
                onValueChange = { todoTitle = it },
                label = { Text("Title", color = customColors.headerText) },
                modifier = Modifier.weight(1f),
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
            if (showStatus) {
                Spacer(modifier = Modifier.width(4.dp))
                Checkbox(
                    checked = isCompleted,
                    onCheckedChange = { isCompleted = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = customColors.fabBackground,
                        uncheckedColor = customColors.cardText,
                        checkmarkColor = customColors.fabIcon
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description", color = customColors.headerText) },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 5,
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

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onCancel) {
                Text("Cancel", color = customColors.headerText)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onSave(todoTitle, description, isCompleted) },
                enabled = todoTitle.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = customColors.fabBackground,
                    contentColor = customColors.fabIcon
                )
            ) {
                Text("Done")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}