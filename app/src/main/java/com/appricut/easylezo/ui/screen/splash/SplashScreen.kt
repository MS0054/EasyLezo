package com.appricut.easylezo.ui.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.appricut.easylezo.ui.Screen
import com.appricut.easylezo.ui.screen.main.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SplashScreen(
    onNavigate: (String) -> Unit
) {
    val auth = Firebase.auth
    val mainVm: MainViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        val current = auth.currentUser
        if (current == null) {
            onNavigate(Screen.Auth.route)
        } else {
            // check admin and then navigate accordingly
            mainVm.checkAdmin()
            // wait until isAdmin resolved
            snapshotFlow { mainVm.isAdmin.value }
                .filterNotNull()
                .first()
                .let { isAdmin ->
                    if (isAdmin) onNavigate(Screen.Admin.route) else onNavigate(Screen.UserHome.route)
                }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}