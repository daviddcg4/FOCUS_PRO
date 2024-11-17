import android.os.SystemClock
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.focuspro.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: TaskViewModel = viewModel()) {
    // Variables to track the app runtime
    var startTime by remember { mutableStateOf(SystemClock.elapsedRealtime()) }
    var elapsedTime by remember { mutableStateOf("0m") }

    // LaunchedEffect to update elapsed time every second
    LaunchedEffect(true) {
        while (true) {
            val currentTime = SystemClock.elapsedRealtime()
            val secondsElapsed = (currentTime - startTime) / 1000
            val minutes = secondsElapsed / 60
            val seconds = secondsElapsed % 60
            elapsedTime = String.format("%dm %ds", minutes, seconds)
            kotlinx.coroutines.delay(1000) // Update every second
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF4C5990),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        // Use innerPadding to prevent content from overlapping with the TopAppBar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Summary Section
            SummarySection(elapsedTime = elapsedTime)
            Spacer(modifier = Modifier.height(16.dp))

            // Task List Section
            TaskListSection(viewModel = viewModel)
        }
    }
}

@Composable
fun SummarySection(elapsedTime: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        SummaryCard(title = stringResource(id = R.string.focus_time), value = elapsedTime)
        TasksCompletedCard(completed = 7, total = 10) // You can adjust this dynamically
    }
}

@Composable
fun TasksCompletedCard(completed: Int, total: Int) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.tasks_completed),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF6200EA)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = completed.toFloat() / total,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .padding(vertical = 4.dp),
                color = Color(0xFF03DAC5),
                trackColor = Color(0xFFE0E0E0)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$completed / $total",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun SummaryCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 14.sp)
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TaskListSection(viewModel: TaskViewModel) {
    // Observe the task list
    val taskList by viewModel.taskList.collectAsState(initial = emptyList())

    Text(stringResource(id = R.string.priority_tasks), fontSize = 18.sp)
    Spacer(modifier = Modifier.height(8.dp))

    // Show tasks dynamically
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(taskList) { task ->
            TaskItem(taskName = task.title)
        }
    }
}

@Composable
fun TaskItem(taskName: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Text(text = taskName, modifier = Modifier.padding(16.dp), fontSize = 16.sp)
    }
}
