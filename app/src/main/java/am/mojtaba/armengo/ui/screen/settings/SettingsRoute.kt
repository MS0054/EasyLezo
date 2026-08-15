package am.mojtaba.armengo.ui.screen.settings

import am.mojtaba.armengo.ui.UiEvent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingsRoute(
    snackBarHostState: SnackbarHostState,
    onBack: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    SettingsScreen(
        uiState = uiState,
        onBackClick = onBack,
        onThemeToggle = { isDark ->
            viewModel.toggleTheme(isDark)
        },
        onPolicyClick = {
            if (uiState.settings.policyUrl.isNotEmpty()) {
                uriHandler.openUri(uiState.settings.policyUrl)
            }
        },
        onTermsClick = {
            if (uiState.settings.termsUrl.isNotEmpty()) {
                uriHandler.openUri(uiState.settings.termsUrl)
            }
        },
        onLoginClick = onLoginClick,
        onSignOutClick = {
            viewModel.signOut()
        }
    )
}
