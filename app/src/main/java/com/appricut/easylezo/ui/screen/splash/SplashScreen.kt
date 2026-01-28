package com.appricut.easylezo.ui.screen.splash

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.appricut.easylezo.ui.Screen
import com.appricut.easylezo.ui.screen.auth.AuthViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(
    onNavigate: (String) -> Unit
) {
    val auth = Firebase.auth
    val authVm: AuthViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        val current = auth.currentUser
        if (current == null) {
            onNavigate(Screen.UserHome.route)
        } else {
            authVm.checkAdmin()
            authVm.isAdmin.filterNotNull().first().let { isAdmin ->
                if (isAdmin) onNavigate(Screen.Admin.route)
                else onNavigate(Screen.UserHome.route)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
