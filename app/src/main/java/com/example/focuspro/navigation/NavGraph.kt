package com.example.focuspro.navigation

import ChatScreen
import DailySummaryScreen
import DashboardScreen
import HomeScreen
import LanguageViewModel

import LoginScreen
import MotivationScreen
import ProgressScreen
import RankingScreen
import RegisterScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.focuspro.analyticsReports.ActivityRingsScreen
import com.example.focuspro.pomodoro.PomodoroTimerScreen
import com.example.focuspro.profile.ProfileScreen
import com.example.focuspro.taskManager.view.TaskManagerScreen
import com.example.focuspro.ui.SettingsScreen

import com.example.focuspro.viewmodel.AuthViewModel
import com.example.focuspro.viewmodel.ThemeViewModel

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val SETTINGS = "settings"
    const val PROFILE = "profile"
    const val POMODORO = "pomodoro"
    const val TASK_MANAGER = "task_manager"
    const val PROGRESS = "progress"
    const val MOTIVATION = "motivation"
    const val DAILY_SUMMARY = "daily_summary"
    const val DASHBOARD = "dashboard"
    const val CHAT = "chat"
    const val ACTIVITY_RINGS = "activity_rings"
    const val RANKING = "ranking"
}

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    languageViewModel: LanguageViewModel,
    themeViewModel: ThemeViewModel
) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(authViewModel = authViewModel, navController = navController)
        }
        composable(Routes.REGISTER) {
            RegisterScreen(authViewModel = authViewModel, navController = navController)
        }
        composable(Routes.HOME) {
            HomeScreen(authViewModel = authViewModel, navController = navController)
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(
                authViewModel = authViewModel,
                navController = navController,
                languageViewModel = languageViewModel,
                themeViewModel = themeViewModel
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(authViewModel = authViewModel, navController = navController)
        }
        composable(Routes.POMODORO) {
            PomodoroTimerScreen(navController = navController)
        }
        composable(Routes.TASK_MANAGER) {
            TaskManagerScreen(navController = navController)
        }
        composable(Routes.PROGRESS) {
            ProgressScreen(navController = navController)
        }
        composable(Routes.MOTIVATION) {
            MotivationScreen(navController = navController)
        }
        composable(Routes.DAILY_SUMMARY) {
            DailySummaryScreen(navController = navController)
        }
        composable(Routes.DASHBOARD) {
            DashboardScreen(navController = navController)
        }
        composable(Routes.CHAT) {
            ChatScreen(navController = navController)
        }
        composable(Routes.ACTIVITY_RINGS) {
            ActivityRingsScreen(navController = navController)
        }
        composable(Routes.RANKING) {
            RankingScreen()
        }
    }
}
