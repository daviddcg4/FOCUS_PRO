
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.focuspro.R
import kotlin.random.Random

@Composable
fun MotivationScreen(navController: NavController) {
    // Lista de notas motivacioneles
    val quotes = listOf(
        stringResource(id = R.string.motivational_quote_1),
        stringResource(id = R.string.motivational_quote_2),
        stringResource(id = R.string.motivational_quote_3),
        stringResource(id = R.string.motivational_quote_4),
        stringResource(id = R.string.motivational_quote_5)
    )

    // estado de la tarea actual
    var currentQuote by remember { mutableStateOf(quotes[0]) }

    // Lista de objetivos personales
    val goals = listOf(
        stringResource(id = R.string.goal_read_book),
        stringResource(id = R.string.goal_exercise),
        stringResource(id = R.string.goal_learn_skill),
        stringResource(id = R.string.goal_meditate),
        stringResource(id = R.string.goal_save_money),

        ).toMutableStateList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // nota motivacional
        Text(
            text = currentQuote,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button para cambiar la nota
        Button(onClick = {
            currentQuote = quotes[Random.nextInt(quotes.size)]
        }) {
            Text(text = stringResource(id = R.string.get_new_quote))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Title
        Text(
            text = stringResource(id = R.string.my_goals),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de objetivos
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(goals.size) { index ->
                GoalItem(goal = goals[index])
            }
        }
    }
}

@Composable
fun GoalItem(goal: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = Color.LightGray,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText(text = goal)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MotivationScreenPreview() {

    MotivationScreen(navController = rememberNavController())
}
