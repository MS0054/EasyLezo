package am.mojtaba.armengo.ui

import am.mojtaba.armengo.ui.ads.AdBanner
import am.mojtaba.armengo.ui.screen.auth.AuthRoute
import am.mojtaba.armengo.ui.screen.auth.AuthScreen
import am.mojtaba.armengo.ui.screen.auth.AuthViewModel
import am.mojtaba.armengo.ui.screen.category.CategoryRoute
import am.mojtaba.armengo.ui.screen.sentence.SentenceRoute
import am.mojtaba.armengo.ui.screen.settings.SettingsRoute
import am.mojtaba.armengo.ui.screen.splash.SplashRoute
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Auth : Screen("auth")
    data object Category : Screen("category")
    data object Settings : Screen("settings")
    data object Sentence : Screen("sentence/{categoryId}/{categoryName}") {
        fun createRoute(
            categoryId: String,
            categoryName: String
        ) = "sentence/$categoryId/$categoryName"
    }
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = { AdBanner() },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { ـ ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route
        ) {
            composable(Screen.Splash.route) {
                SplashRoute(
                    snackBarHostState = snackbarHostState,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Screen.Settings.route) {
                SettingsRoute(
                    snackBarHostState = snackbarHostState,
                    onBack = {
                        navController.popBackStack()
                    },
                    onLoginClick = {
                        navController.navigate(Screen.Auth.route)
                    }
                )
            }

            composable(Screen.Auth.route) {
                AuthRoute(
                    snackBarHostState = snackbarHostState,
                    onAuthSuccess = {
                        navController.navigate(Screen.Splash.route) {
                            popUpTo(Screen.Auth.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Screen.Category.route) {
                CategoryRoute(
                    snackBarHostState = snackbarHostState,
                    onCategorySelected = { category ->
                        navController.navigate(
                            Screen.Sentence.createRoute(category.id, category.fromText)
                        )
                    },
                    onProfileSelected = {
                        navController.navigate(Screen.Settings.route)
                    }
                )
            }

            composable(Screen.Sentence.route) {
                SentenceRoute(
                    snackBarHostState = snackbarHostState,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}