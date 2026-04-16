package com.appricut.easylezo.ui

import androidx.compose.runtime.Composable
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

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Auth : Screen("auth")
    object Category : Screen("category")
    object Sentences : Screen("sentences/{categoryId}/{categoryName}") {
        fun createRoute(
            categoryId: String,
            categoryName: String
        ) = "sentences/$categoryId/$categoryName"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost( navController,Screen.Splash.route) {

        composable(Screen.Splash.route) {
            val splashVM: SplashViewModel = hiltViewModel()
            SplashScreen(splashVM) { route ->
                navController.navigate(route) { popUpTo(Screen.Splash.route) { inclusive = true } }
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
            val categoryViewModel: CategoryViewModel = hiltViewModel()
            CategoryListScreen(
                categoryViewModel,
                { category ->
                    navController.navigate(Screen.Sentences.createRoute(category.id, category.name))
                }, {
                    navController.navigate(Screen.Auth.route)
                })
        }

        composable(Screen.Sentences.route,) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            val sentenceViewModel: SentenceViewModel = hiltViewModel()
            SentenceListScreen(categoryId, categoryName, sentenceViewModel)
        }

//        composable(Screen.Admin.route) {
//            val authVm: AuthViewModel = hiltViewModel()
//            val adminVm: AdminViewModel = hiltViewModel()
//            AdminScreen( adminVm, authVm) {
//                navController.navigate(Screen.Splash.route) {
//                    popUpTo(Screen.Auth.route) {
//                        inclusive = true
//                    }
//                }
//            }
//        }
    }
}
