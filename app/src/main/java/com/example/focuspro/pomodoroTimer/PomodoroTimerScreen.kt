package com.example.focuspro.pomodoro

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.res.stringResource
import com.example.focuspro.R

@Composable
fun PomodoroTimerScreen(navController: NavController) {
    var focusTime by remember { mutableStateOf(25 * 60) } // Tiempo de enfoque
    var breakTime by remember { mutableStateOf(5 * 60) } // Tiempo de descanso
    var timeLeft by remember { mutableStateOf(focusTime) } // Tiempo restante
    var isRunning by remember { mutableStateOf(false) }
    var isFocusMode by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    // Barra de progreso basada en el tiempo restante
    val progress = timeLeft.toFloat() / if (isFocusMode) focusTime else breakTime

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.pomodoro_timer_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Progreso del temporizador
        CircularProgressIndicator(
            progress = progress,
            strokeWidth = 8.dp,
            color = if (isFocusMode) Color(0xFF4CAF50) else Color(0xFF2196F3),
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        )

        Text(
            text = formatTime(timeLeft),
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Bold,
                color = if (isFocusMode) Color(0xFF4CAF50) else Color(0xFF2196F3)
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = if (isFocusMode) stringResource(id = R.string.focus_mode) else stringResource(id = R.string.break_mode),
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.secondary),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Ajustes de tiempo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TimeInputField(
                label = stringResource(id = R.string.focus_time_label),
                time = focusTime,
                onTimeChange = { focusTime = it * 60 },
                enabled = !isRunning
            )
            TimeInputField(
                label = stringResource(id = R.string.break_time_label),
                time = breakTime,
                onTimeChange = { breakTime = it * 60 },
                enabled = !isRunning
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Controles del temporizador
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Button(
                onClick = {
                    if (!isRunning) {
                        isRunning = true
                        timeLeft = if (isFocusMode) focusTime else breakTime
                        coroutineScope.launch {
                            while (isRunning) {
                                delay(1000L)
                                timeLeft--

                                if (timeLeft <= 0) {
                                    isFocusMode = !isFocusMode
                                    timeLeft = if (isFocusMode) focusTime else breakTime
                                }
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(id = R.string.start_button_label))
            }

            Button(
                onClick = { isRunning = false },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(stringResource(id = R.string.pause_button_label))
            }

            Button(
                onClick = {
                    isRunning = false
                    timeLeft = focusTime
                    isFocusMode = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(stringResource(id = R.string.reset_button_label))
            }
        }
    }
}

@Composable
fun TimeInputField(label: String, time: Int, onTimeChange: (Int) -> Unit, enabled: Boolean) {
    var inputValue by remember { mutableStateOf(time / 60) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = inputValue.toString(),
            onValueChange = {
                inputValue = it.toIntOrNull() ?: 0
                onTimeChange(inputValue)
            },
            enabled = enabled,
            modifier = Modifier.width(100.dp),
            singleLine = true
        )
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}
