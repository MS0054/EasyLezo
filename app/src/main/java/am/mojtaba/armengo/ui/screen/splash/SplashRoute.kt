package am.mojtaba.armengo.ui.screen.splash

import am.mojtaba.armengo.core.data.datastore.enums.UpdateType
import am.mojtaba.armengo.core.domain.model.UpdateResult
import am.mojtaba.armengo.ui.Screen
import am.mojtaba.armengo.ui.UiEvent
import android.content.Intent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay

@Composable
fun SplashRoute(
    snackBarHostState: SnackbarHostState,
    onNavigate: (String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showSheet by remember { mutableStateOf(false) }
    var updateResult by remember { mutableStateOf<UpdateResult?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    LaunchedEffect(uiState.updateStatus) {
        when (val state = uiState.updateStatus) {
            is UpdateStatus.Success -> {
                when (state.updateResult.type) {
                    UpdateType.OPTIONAL, UpdateType.FORCE -> {
                        updateResult = state.updateResult
                        showSheet = true
                    }
                    UpdateType.NONE -> {
                        delay(200)
                        onNavigate(Screen.Category.route)
                    }
                }
            }
            else -> {}
        }
    }

    SplashScreen(
        uiState = uiState,
        onRefreshClick = { viewModel.start() }
    )

    if (showSheet) {
        updateResult?.let { result ->
            UpdateAppBottomSheet(
                updateResult = result,
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