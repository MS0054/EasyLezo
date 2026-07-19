package am.mojtaba.armengo.ui

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import am.mojtaba.armengo.ui.screen.auth.AuthScreen
import am.mojtaba.armengo.ui.screen.auth.AuthViewModel
import am.mojtaba.armengo.ui.screen.category.CategoryRoute
import am.mojtaba.armengo.ui.screen.splash.SplashScreen
import am.mojtaba.armengo.ui.screen.splash.SplashViewModel
import am.mojtaba.armengo.ui.screen.sentence.SentenceRoute
import am.mojtaba.armengo.ui.screen.settings.SettingsScreen
import am.mojtaba.armengo.ui.screen.settings.SettingsViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Auth : Screen("auth")
    object Category : Screen("category")
    object Settings : Screen("settings")
    object Sentence : Screen("sentence/{categoryId}/{categoryName}") {
        fun createRoute(
            categoryId: String,
            categoryName: String
        ) = "sentence/$categoryId/$categoryName"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {


    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { ـ ->

        NavHost(
            navController,
            Screen.Splash.route
        ) {
            composable(Screen.Splash.route) {
                val splashVM: SplashViewModel = hiltViewModel()
                SplashScreen(splashVM) { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            }
            composable(Screen.Settings.route) {
                val settingsVm: SettingsViewModel = hiltViewModel()
                SettingsScreen(settingsVm) {
                    navController.popBackStack()
                }
            }
            composable(Screen.Auth.route) {
                val authVm: AuthViewModel = hiltViewModel()
                AuthScreen(authVm) {
                    navController.navigate(Screen.Splash.route) {
                        popUpTo(Screen.Auth.route) {
                            inclusive = true
                        }
                    }
                }
            }
            composable(Screen.Category.route) {
                CategoryRoute(
                    snackbarHostState,
                    {
                        navController.navigate(Screen.Sentence.createRoute(it.id, it.fromText))
                    }, {
                        navController.navigate(Screen.Settings.route)
                    })
            }
            composable(Screen.Sentence.route) {
                SentenceRoute(
                    snackbarHostState,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
