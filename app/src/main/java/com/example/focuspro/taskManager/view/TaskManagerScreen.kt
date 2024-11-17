package com.example.focuspro.taskManager.view

import TaskViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.focuspro.R
import com.example.focuspro.taskManager.model.entity.Task
import com.example.focuspro.taskManager.model.entity.TaskPriority


@Composable
fun TaskManagerScreen(navController: NavController, viewModel: TaskViewModel = viewModel()) {
    var showDialog by remember { mutableStateOf(false) }
    // Recolecta taskList como estado de Compose
    val taskList by viewModel.taskList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.task_manager_title),
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(onClick = { showDialog = true }) {
            Text(stringResource(id = R.string.add_task_button))
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(taskList) { task -> // Usa taskList aquí
                TaskItem(
                    task = task,
                    onTaskCompleted = { viewModel.toggleTaskCompletion(it) },
                    onTaskDeleted = { viewModel.deleteTask(it) }
                )
            }
        }

        if (showDialog) {
            TaskDialog(
                onDismiss = { showDialog = false },
                onTaskAdded = { title, priority ->
                    viewModel.addTask(title, priority)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onTaskCompleted: (Task) -> Unit,
    onTaskDeleted: (Task) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Checkbox para marcar la tarea como completada
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onTaskCompleted(task) }
        )

        // Texto para mostrar el título de la tarea
        Text(
            text = task.title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            style = TextStyle(fontSize = 16.sp)
        )

        // Indicativo de prioridad
        val priorityColor = when (task.priority) {
            TaskPriority.LOW -> Color.Green
            TaskPriority.MEDIUM -> Color.Yellow
            TaskPriority.HIGH -> Color.Red
        }

        // Mostrar un círculo de color según la prioridad
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(priorityColor, shape = MaterialTheme.shapes.small)
                .padding(4.dp)
        )

        // Botón para eliminar la tarea
        IconButton(onClick = { onTaskDeleted(task) }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.delete_task_content_description),
                tint = Color.Red
            )
        }
    }
}

@Composable
fun TaskDialog(onDismiss: () -> Unit, onTaskAdded: (String, TaskPriority) -> Unit) {
    var taskTitle by remember { mutableStateOf("") }
    var taskPriority by remember { mutableStateOf(TaskPriority.LOW) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.add_task_dialog_title)) },
        text = {
            Column {
                Text(stringResource(id = R.string.task_title_label))
                BasicTextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(stringResource(id = R.string.priority_label))
                Row {
                    TaskPriority.values().forEach { priority ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            RadioButton(
                                selected = (taskPriority == priority),
                                onClick = { taskPriority = priority }
                            )
                            Text(
                                text = stringResource(
                                    id = when (priority) {
                                        TaskPriority.LOW -> R.string.priority_low
                                        TaskPriority.MEDIUM -> R.string.priority_medium
                                        TaskPriority.HIGH -> R.string.priority_high
                                    }
                                ),
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (taskTitle.isNotBlank()) {
                        onTaskAdded(taskTitle, taskPriority)
                        taskTitle = "" // Resetear el título después de agregar
                    }
                }
            ) {
                Text(stringResource(id = R.string.add_task_button_text))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(id = R.string.cancel_button_text))
            }
        }
    )
}
