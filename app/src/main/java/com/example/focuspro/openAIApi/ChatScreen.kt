import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun ChatScreen(navController: NavHostController, viewModel: ChatViewModel = viewModel()) {
    var userMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = userMessage,
            onValueChange = { userMessage = it },
            label = { Text("Enter your message") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.sendMessageToOpenAi(userMessage) }) {
            Text("Send")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Response: ${viewModel.responseText}")
    }
}
