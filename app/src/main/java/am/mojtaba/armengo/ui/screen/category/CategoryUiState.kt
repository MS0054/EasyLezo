package am.mojtaba.armengo.ui.screen.category

import am.mojtaba.armengo.core.domain.model.AppLanguages
import am.mojtaba.armengo.core.domain.model.Category

data class CategoryUiState(
    val isLoading: Boolean = false,
    val appLanguages: AppLanguages = AppLanguages(),
    val categories: List<Category> = emptyList()
)