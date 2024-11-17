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
    var focusTime by remember { mutableStateOf(25 * 60) } // Focus time in seconds
    var breakTime by remember { mutableStateOf(5 * 60) } // Break time in seconds
    var timeLeft by remember { mutableStateOf(focusTime) } // Remaining time
    var isRunning by remember { mutableStateOf(false) }
    var isFocusMode by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.pomodoro_timer_title),
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = formatTime(timeLeft),
            style = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.Red),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Section for adjusting times
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TimeInputField(
                label = stringResource(id = R.string.focus_time_label),
                time = focusTime,
                onTimeChange = { focusTime = it * 60 }
            )
            TimeInputField(
                label = stringResource(id = R.string.break_time_label),
                time = breakTime,
                onTimeChange = { breakTime = it * 60 }
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Button(onClick = {
                if (!isRunning) {
                    isRunning = true
                    timeLeft = focusTime // Start with focus time
                    isFocusMode = true // Focus mode
                    coroutineScope.launch {
                        while (isRunning) {
                            delay(1000L)
                            timeLeft--

                            // Switch between focus and break
                            if (timeLeft <= 0) {
                                if (isFocusMode) {
                                    timeLeft = breakTime // Switch to break time
                                } else {
                                    timeLeft = focusTime // Switch to focus time
                                }
                                isFocusMode = !isFocusMode // Toggle mode
                            }
                        }
                    }
                }
            }) {
                Text(stringResource(id = R.string.start_button_label))
            }

            Button(onClick = {
                isRunning = false
            }) {
                Text(stringResource(id = R.string.pause_button_label))
            }

            Button(onClick = {
                isRunning = false
                timeLeft = focusTime // Reset to focus time
            }) {
                Text(stringResource(id = R.string.reset_button_label))
            }
        }
    }
}

@Composable
fun TimeInputField(label: String, time: Int, onTimeChange: (Int) -> Unit) {
    var inputValue by remember { mutableStateOf(time / 60) } // Display time in minutes

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = TextStyle(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = inputValue.toString(),
            onValueChange = {
                inputValue = it.toIntOrNull() ?: 0 // Change the value to integer
                onTimeChange(inputValue) // Update the time
            },
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
