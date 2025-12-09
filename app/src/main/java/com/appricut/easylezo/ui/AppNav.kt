package com.appricut.easylezo.ui


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appricut.easylezo.ui.screen.auth.AuthViewModel
import com.appricut.easylezo.ui.screen.main.AdminScreen
import com.appricut.easylezo.ui.screen.auth.AuthScreen
import com.appricut.easylezo.ui.screen.main.MainViewModel
import com.appricut.easylezo.ui.screen.splash.SplashScreen
import com.appricut.easylezo.ui.screen.main.UserHomeScreen

sealed class Screen(val route: String) {
    object Splash: Screen("splash")
    object Auth: Screen("auth")
    object UserHome: Screen("user_home")
    object Admin: Screen("admin")
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(onNavigate = { route ->
                navController.navigate(route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Auth.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            AuthScreen(viewModel = authViewModel, onSuccess = {
                navController.navigate(Screen.UserHome.route) {
                    popUpTo(Screen.Auth.route) { inclusive = true }
                }
            })
        }
        composable(Screen.UserHome.route) {
            val mainVm: MainViewModel = hiltViewModel()
            UserHomeScreen(viewModel = mainVm)
        }
        composable(Screen.Admin.route) {
            val mainVm: MainViewModel = hiltViewModel()
            AdminScreen()
        }
    }
}