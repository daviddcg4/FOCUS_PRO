import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.focuspro.R
import com.example.focuspro.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, authViewModel: AuthViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Lista de elementos del menú
    val menuItems = listOf(
        stringResource(id = R.string.dashboard) to "dashboard",
        stringResource(id = R.string.profile) to "profile",
        stringResource(id = R.string.pomodoro_timer) to "pomodoro",
        stringResource(id = R.string.task_manager) to "task_manager",
        stringResource(id = R.string.motivation) to "motivation",
        stringResource(id = R.string.logout) to "login",
        stringResource(id = R.string.settings) to "settings"
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxSize(),
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(280.dp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.primary) // Fondo del drawer
            ) {
                DrawerContent(menuItems) { selectedMenuItem ->
                    // Cerrar el drawer antes de navegar
                    scope.launch {
                        drawerState.close()
                        if (selectedMenuItem == "login") {
                            authViewModel.logoutUser() // Cerrar sesión
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            navController.navigate(selectedMenuItem)
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(id = R.string.home_title),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            }
        ) { paddingValues ->
            MainContent(paddingValues)
        }
    }
}

@Composable
fun DrawerContent(menuItems: List<Pair<String, String>>, onMenuItemClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        // Encabezado del menú
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground, // Color de texto
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Divider(
            color = MaterialTheme.colorScheme.onBackground,
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Elementos del menú con iconos
        menuItems.forEach { (itemName, route) ->
            MenuItem(itemName, route, onMenuItemClick)
        }
    }
}

@Composable
fun MenuItem(itemName: String, route: String, onMenuItemClick: (String) -> Unit) {
    val icon = when (route) {
        "dashboard" -> Icons.Default.Home
        "profile" -> Icons.Default.AccountCircle
        "pomodoro" -> Icons.Default.Refresh
        "task_manager" -> Icons.Default.CheckCircle
        "motivation" -> Icons.Default.Star
        "logout" -> Icons.Default.ExitToApp
        "settings" -> Icons.Default.Settings
        else -> Icons.Default.Menu
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { onMenuItemClick(route) }
            .padding(horizontal = 12.dp)
    ) {
        Icon(
            icon,
            contentDescription = itemName,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(end = 12.dp)
        )
        Text(
            text = itemName,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun MainContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Text(
            stringResource(id = R.string.welcome_message),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            stringResource(id = R.string.home_description),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
