import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.focuspro.R

@Composable
fun DailySummaryScreen(navController: NavController) {
    // datos simulados
    val totalActiveTime = "3h 45m"
    val completedTasks = listOf(
        stringResource(id = R.string.task_workout),
        stringResource(id = R.string.task_read_pages),
        stringResource(id = R.string.task_meeting_team)
    )
    val pendingTasks = listOf(
        stringResource(id = R.string.task_finish_report),
        stringResource(id = R.string.task_grocery_shopping)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // titulo
        Text(
            text = stringResource(id = R.string.daily_summary),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // estadisticas
        DailyStatistics(totalActiveTime)

        Spacer(modifier = Modifier.height(24.dp))

        // tareas completadas
        SectionTitle(title = stringResource(id = R.string.completed_tasks))
        TaskList(tasks = completedTasks, completed = true)

        Spacer(modifier = Modifier.height(24.dp))

        // tareas pendientes
        SectionTitle(title = stringResource(id = R.string.pending_tasks))
        TaskList(tasks = pendingTasks, completed = false)
    }
}

@Composable
fun DailyStatistics(totalTime: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.total_active_time, totalTime),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun TaskList(tasks: List<String>, completed: Boolean) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasks.size) { index ->
            TaskItem(taskName = tasks[index], completed = completed)
        }
    }
}

@Composable
fun TaskItem(taskName: String, completed: Boolean) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = if (completed) Color(0xFFD1E7DD) else Color(0xFFF8D7DA),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = taskName,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = if (completed) Color(0xFF0F5132) else Color(0xFF842029)
            )
        }
    }
}
