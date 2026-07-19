package am.mojtaba.armengo.ui.screen.category

import am.mojtaba.armengo.core.domain.model.Category
import am.mojtaba.armengo.ui.UiEvent
import am.mojtaba.armengo.ui.screen.language.sheet.AppLanguageSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CategoryRoute(
    snackBarHostState: SnackbarHostState,
    onCategorySelected: (Category) -> Unit,
    onProfileSelected: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showSelectLanguageSheet by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    if (showSelectLanguageSheet) {
        AppLanguageSheet(
            appLanguages = uiState.appLanguages,
            onLanguageSelected = { language ->
                viewModel.updateUserAppLanguages(
                    uiState.appLanguages,
                    language
                )
            },
            onDismiss = {
                showSelectLanguageSheet = false
            }
        )
    }

    CategoryScreen(
        uiState = uiState,
        onCategorySelected = onCategorySelected,
        onProfileSelected = onProfileSelected,
        onLanguageClick = {
            showSelectLanguageSheet = true
        }
    )
}