package com.example.focuspro.ui

import LanguageViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.focuspro.R
import com.example.focuspro.viewmodel.AuthViewModel
import com.example.focuspro.viewmodel.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    languageViewModel: LanguageViewModel,
    themeViewModel: ThemeViewModel
) {
    var isNotificationsEnabled by remember { mutableStateOf(true) }
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    val languages = listOf("en", "es", "fr", "de") // Idiomas disponibles
    var showTermsDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.settings)) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Categoría: General
            item { SettingsCategoryTitle(stringResource(id = R.string.general)) }

            item {
                ThemeSetting(
                    isDarkTheme = isDarkTheme,
                    onThemeChanged = { themeViewModel.toggleTheme() }
                )
            }

//            item {
//                LanguageSetting(
//                    selectedLanguage = selectedLanguage,
//                    languages = languages,
//                    onLanguageSelected = { language ->
//                        languageViewModel.changeLanguage(language)
//                    }
//                )
//            }

            // Categoría: Notificaciones
            item { SettingsCategoryTitle(stringResource(id = R.string.notifications)) }

            item {
                NotificationSetting(
                    isNotificationsEnabled = isNotificationsEnabled,
                    onNotificationsToggled = { isNotificationsEnabled = it }
                )
            }

            // Categoría: Información
            item { SettingsCategoryTitle(stringResource(id = R.string.information)) }

            item {
                InformationSetting(label = stringResource(id = R.string.terms_conditions)) {
                    showTermsDialog = true
                }
            }

            item {
                InformationSetting(label = stringResource(id = R.string.about_us)) { /* Acción para "Sobre nosotros" */ }
            }
        }
    }

    // Diálogo para los términos y condiciones
    if (showTermsDialog) {
        AlertDialog(
            onDismissRequest = { showTermsDialog = false },
            title = { Text(stringResource(id = R.string.terms_conditions)) },
            text = { Text(stringResource(id = R.string.terms_and_conditions)) },
            confirmButton = {
                TextButton(onClick = { showTermsDialog = false }) {
                    Text(stringResource(id = R.string.ok))
                }
            }
        )
    }
}

// Títulos de las categorías en la configuración
@Composable
fun SettingsCategoryTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

// Configuración de tema oscuro
@Composable
fun ThemeSetting(isDarkTheme: Boolean, onThemeChanged: (Boolean) -> Unit) {
    SettingItem(
        title = stringResource(id = R.string.dark_theme),
        description = stringResource(id = R.string.dark_theme_description),
        trailingContent = {
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { onThemeChanged(it) }
            )
        }
    )
}

// Configuración de idioma
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSetting(
    selectedLanguage: String,
    languages: List<String>,
    onLanguageSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    SettingItem(
        title = stringResource(id = R.string.language),
        description = stringResource(id = R.string.language_description),
        trailingContent = {
            Box {
                Text(
                    text = selectedLanguage.uppercase(),
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .padding(16.dp)
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    languages.forEach { language ->
                        DropdownMenuItem(
                            onClick = {
                                onLanguageSelected(language)
                                expanded = false
                            },
                            text = { Text(text = language.uppercase()) }
                        )
                    }
                }
            }
        }
    )
}

// Configuración de notificaciones
@Composable
fun NotificationSetting(
    isNotificationsEnabled: Boolean,
    onNotificationsToggled: (Boolean) -> Unit
) {
    SettingItem(
        title = stringResource(id = R.string.notifications),
        description = stringResource(id = R.string.notifications_description),
        trailingContent = {
            Switch(
                checked = isNotificationsEnabled,
                onCheckedChange = { onNotificationsToggled(it) }
            )
        }
    )
}

// Configuración de elementos informativos
@Composable
fun InformationSetting(label: String, onClick: () -> Unit) {
    SettingItem(
        title = label,
        description = "",
        onClick = onClick
    )
}

// Elemento individual de configuración
@Composable
fun SettingItem(
    title: String,
    description: String,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                if (description.isNotEmpty()) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
            if (trailingContent != null) {
                trailingContent()
            }
        }
    }
}
