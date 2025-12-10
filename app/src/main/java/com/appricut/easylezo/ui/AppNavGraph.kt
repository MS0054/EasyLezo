package com.appricut.easylezo.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appricut.easylezo.ui.screen.admin.AdminScreen
import com.appricut.easylezo.ui.screen.admin.AdminViewModel
import com.appricut.easylezo.ui.screen.auth.AuthScreen
import com.appricut.easylezo.ui.screen.auth.AuthViewModel
import com.appricut.easylezo.ui.screen.splash.SplashScreen
import com.appricut.easylezo.ui.screen.user.CategoryListScreen
import com.appricut.easylezo.ui.screen.user.MainViewModel
import com.appricut.easylezo.ui.screen.user.SentenceListScreen

sealed class Screen(val route: String) {
    object Splash: Screen("splash")
    object Auth: Screen("auth")
    object UserHome: Screen("user_home")
    object Admin: Screen("admin")
    object Sentences: Screen("sentences/{categoryId}") {
        fun createRoute(categoryId: String) = "sentences/$categoryId"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen(onNavigate = { route ->
                navController.navigate(route) { popUpTo(Screen.Splash.route) { inclusive = true } }
            })
        }

        composable(Screen.Auth.route) {
            val authVm: AuthViewModel = hiltViewModel()
            AuthScreen(viewModel = authVm, onSuccess = {
                navController.navigate(Screen.UserHome.route) { popUpTo(Screen.Auth.route) { inclusive = true } }
            })
        }

        composable(Screen.UserHome.route) {
            val mainVm: MainViewModel = hiltViewModel()
            CategoryListScreen(viewModel = mainVm) { category ->
                navController.navigate(Screen.Sentences.createRoute(category.id))
            }
        }

        composable(Screen.Sentences.route) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            val mainVm: MainViewModel = hiltViewModel()
            SentenceListScreen(categoryId = categoryId, viewModel = mainVm)
        }

        composable(Screen.Admin.route) {
            val adminVm: AdminViewModel = hiltViewModel()
            AdminScreen(viewModel = adminVm)
        }
    }
}
