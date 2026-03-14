package com.appricut.easylezo.ui.screen.splash

import android.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel,
    onNavigate: (String) -> Unit
) {
//    val auth = Firebase.auth
//

    splashViewModel.start()

    LaunchedEffect(Unit) {
        splashViewModel.screen
            .filterNotNull()
            .first()
            .let { onNavigate(it.route) }
    }




    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Icon(
            painter = painterResource(R.mipmap.sym_def_app_icon),
            contentDescription = "Forward",
        )
    }
}
