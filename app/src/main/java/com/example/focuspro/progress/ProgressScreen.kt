import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.stringResource
import com.example.focuspro.R

@Composable
fun ProgressScreen(navController: NavController) {
    var progress by remember { mutableStateOf(0) }

    val tasks = listOf(
        "Task 1" to 20,
        "Task 2" to 50,
        "Task 3" to 75,
        "Task 4" to 100
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(progress = progress / 100f, modifier = Modifier.size(100.dp))

        Spacer(modifier = Modifier.height(16.dp))

        // Texto de porcentaje de progreso usando string resource
        Text(
            text = stringResource(id = R.string.progress_percentage, progress),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks.size) { index ->
                val (taskName, taskProgress) = tasks[index]
                TaskItem(taskName = taskName, taskProgress = taskProgress)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (progress < 100) {
                progress = (progress + 10).coerceAtMost(100)
            }
        }) {
            Text(text = stringResource(id = R.string.increase_progress_button))
        }
    }
}

@Composable
fun TaskItem(taskName: String, taskProgress: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, shape = MaterialTheme.shapes.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = taskName, modifier = Modifier.weight(1f))
        Text(text = stringResource(id = R.string.task_progress_label) + ": $taskProgress%", fontWeight = FontWeight.Bold)
    }
}
