import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Icon
import com.example.focuspro.R

@Composable
fun GoalsScreen() {
    var goalList by remember { mutableStateOf(mutableListOf<Goal>()) }
    var goalInput by remember { mutableStateOf(TextFieldValue("")) }
    var goalIdCounter by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.goals_title),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Input field and add button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            TextField(
                value = goalInput,
                onValueChange = { goalInput = it },
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                placeholder = { Text(stringResource(id = R.string.goal_placeholder)) }
            )
            Button(onClick = {
                if (goalInput.text.isNotBlank()) {
                    goalList.add(Goal(goalIdCounter++, goalInput.text, 0.0f))
                    goalInput = TextFieldValue("")
                }
            }) {
                Text(stringResource(id = R.string.add_goal_button))
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(goalList) { goal ->
                GoalItem(
                    goal = goal,
                    onGoalUpdated = { updatedGoal ->
                        goalList = goalList.map {
                            if (it.id == updatedGoal.id) updatedGoal else it
                        }.toMutableList()
                    },
                    onGoalDeleted = { deletedGoal ->
                        goalList = goalList.filter { it.id != deletedGoal.id }.toMutableList()
                    }
                )
            }
        }
    }
}

@Composable
fun GoalItem(
    goal: Goal,
    onGoalUpdated: (Goal) -> Unit,
    onGoalDeleted: (Goal) -> Unit
) {
    var progress by remember { mutableStateOf(goal.progress) }
    var isCompleted by remember { mutableStateOf(goal.isCompleted) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = goal.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isCompleted) Color.Gray else Color.Black
                ),
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { onGoalDeleted(goal) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete_goal),
                    tint = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            ProgressRing(progress = progress)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                if (progress < 1.0f) {
                    progress += 0.1f
                    if (progress >= 1.0f) isCompleted = true
                    onGoalUpdated(goal.copy(progress = progress, isCompleted = isCompleted))
                }
            }) {
                Text(stringResource(id = R.string.increase_progress_button))
            }
            if (!isCompleted) {
                Button(onClick = {
                    isCompleted = true
                    onGoalUpdated(goal.copy(isCompleted = true, progress = 1.0f))
                }) {
                    Text(stringResource(id = R.string.complete_button))
                }
            }
        }
    }
}

@Composable
fun ProgressRing(progress: Float) {
    Canvas(modifier = Modifier.size(60.dp)) {
        // Background ring
        drawArc(
            color = Color.LightGray,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
        )
        // Progress
        drawArc(
            color = Color.Green,
            startAngle = -90f,
            sweepAngle = 360 * progress,
            useCenter = false,
            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GoalsScreenPreview() {
    GoalsScreen()
}
