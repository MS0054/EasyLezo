package am.mojtaba.armengo.ui.screen.auth

import am.mojtaba.armengo.ui.UiEvent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AuthRoute(
    snackBarHostState: SnackbarHostState,
    onAuthSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    AuthScreen(
        uiState = uiState,
        onSignInClick = { email, password ->
            viewModel.signIn(email, password, onSuccess = onAuthSuccess)
        },
        onSignUpClick = { email, password, displayName ->
            viewModel.signUp(email, password, displayName, onSuccess = onAuthSuccess)
        },
        onGoogleSignInClick = { idToken ->
            viewModel.signInWithGoogle(idToken, onSuccess = onAuthSuccess)
        }
    )
}