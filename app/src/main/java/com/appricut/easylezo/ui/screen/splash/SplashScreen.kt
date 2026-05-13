package com.appricut.easylezo.ui.screen.splash

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.appricut.easylezo.core.domain.model.UpdateType
import com.appricut.easylezo.ui.Screen
import com.appricut.easylezo.ui.screen.splash.sheet.UpdateBottomSheet
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.appricut.easylezo.app.R
import com.appricut.easylezo.core.domain.model.UpdateResult

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    val updateState by splashViewModel.updateState.collectAsState()
    var showSheet by remember { mutableStateOf(true) }
    var updateResult by remember { mutableStateOf<UpdateResult?>(null) }

//    LaunchedEffect(Unit) {
//        splashViewModel.start(context)
//    }
//    LaunchedEffect(Unit) {
//        splashViewModel.screen.filterNotNull().first().let { onNavigate(it.route) }
//    }

    when (val state = updateState) {
        is UpdateStatus.Success -> {
            when (state.updateResult.type) {
                UpdateType.OPTIONAL-> {
                    updateResult = state.updateResult
                    showSheet = true

                }
                UpdateType.FORCE -> {
                    updateResult = state.updateResult
                    showSheet = true
                }
                UpdateType.NONE -> {
                    LaunchedEffect(Unit) { onNavigate(Screen.Category.route) }
                }
            }
        }
        is UpdateStatus.Error -> {
            LaunchedEffect(Unit) { onNavigate(Screen.Category.route) }
        }
        UpdateStatus.Idle -> {
            Log.i("XoXo", "Idle")
            // نمایش لودینگ کوچک اگر نیاز بود
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AsyncImage(
            model = R.drawable.splash_image,
            contentDescription = null,
        )
    }


    if (showSheet) {
        updateResult?.let {
            UpdateBottomSheet(
                it ,
                onUpdateClick = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                },
                onDismiss = {
                    showSheet = false
                    onNavigate(Screen.Category.route)
                }
            )
        }
    }
}
