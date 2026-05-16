package com.appricut.easylezo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appricut.easylezo.ui.screen.auth.AuthScreen
import com.appricut.easylezo.ui.screen.auth.AuthViewModel
import com.appricut.easylezo.ui.screen.splash.SplashScreen
import com.appricut.easylezo.ui.screen.splash.SplashViewModel
import com.appricut.easylezo.ui.screen.category.CategoryListScreen
import com.appricut.easylezo.ui.screen.category.CategoryViewModel
import com.appricut.easylezo.ui.screen.sentence.SentenceListScreen
import com.appricut.easylezo.ui.screen.sentence.SentenceViewModel
import com.appricut.easylezo.ui.screen.settings.SettingsScreen
import com.appricut.easylezo.ui.screen.settings.SettingsViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Auth : Screen("auth")
    object Category : Screen("category")
    object Settings : Screen("settings")
    object Sentences : Screen("sentences/{categoryId}/{categoryName}") {
        fun createRoute(
            categoryId: String,
            categoryName: String
        ) = "sentences/$categoryId/$categoryName"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    val splashVM: SplashViewModel = hiltViewModel()
    val settingsVm: SettingsViewModel = hiltViewModel()
    val authVm: AuthViewModel = hiltViewModel()
    val categoryViewModel: CategoryViewModel = hiltViewModel()
    val sentenceViewModel: SentenceViewModel = hiltViewModel()

    NavHost( navController,Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(splashVM) { route ->
                navController.navigate(route) { popUpTo(Screen.Splash.route) { inclusive = true } }
            }
        }
        composable(Screen.Settings.route) {
            SettingsScreen (settingsVm){
                navController.popBackStack()            }
        }
        composable(Screen.Auth.route) {
            AuthScreen(authVm) {
                navController.navigate(Screen.Splash.route) {
                    popUpTo(Screen.Auth.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(Screen.Category.route) {
            CategoryListScreen(
                categoryViewModel,
                { category ->
                    navController.navigate(Screen.Sentences.createRoute(category.id, category.fromText))
                }, {
                    navController.navigate(Screen.Settings.route)
                })
        }
        composable(Screen.Sentences.route,) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            SentenceListScreen(categoryId, categoryName, sentenceViewModel){
                navController.popBackStack()
            }
        }
    }
}
