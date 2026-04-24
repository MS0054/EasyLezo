package com.appricut.easylezo.admin.ui.screen.category

import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.admin.ui.UiState
import com.appricut.easylezo.admin.ui.screen.BaseViewModel
import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.usecase.category.AddCategoryUseCase
import com.appricut.easylezo.core.domain.usecase.category.ObserveUnsyncedStatusUseCase
import com.appricut.easylezo.core.domain.usecase.category.DeleteCategoryUseCase
import com.appricut.easylezo.core.domain.usecase.category.GetCategoriesUseCase
import com.appricut.easylezo.core.domain.usecase.category.SortCategoryUseCase
import com.appricut.easylezo.core.domain.usecase.category.SyncCategoryUseCase
import com.appricut.easylezo.core.domain.usecase.category.UpdateCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryV @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val sortCategoryUseCase: SortCategoryUseCase,
    private val syncCategoryUseCase: SyncCategoryUseCase,
    private val observeUnsyncedStatusUseCase: ObserveUnsyncedStatusUseCase
) : BaseViewModel() {


    private val _categoryUiState = MutableStateFlow(UiState<List<Category>>())
    val categoryUiState: StateFlow<UiState<List<Category>>> = _categoryUiState.asStateFlow()

    init {
        getCategories()
    }


    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase()
                .onStart {
                    _categoryUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _categoryUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { categories ->
                    _categoryUiState.value = UiState(data = categories)
                    observeSyncStatus()
                }
        }
    }

    private fun observeSyncStatus() {
        viewModelScope.launch {
            observeUnsyncedStatusUseCase().collect { needsSync ->
                activeSyncButton(needsSync)
            }
        }
    }

    fun addCategory(category: Category) {
        launchWithEvent(
            action = { addCategoryUseCase(category) },
            successMessage = "Added"
        )
    }

    fun updateCategory(category: Category) {
        launchWithEvent(
            action = { updateCategoryUseCase(category) },
            successMessage = "Updated"
        )
    }

    fun sortCategory(sorted: List<Category>) {
        launchWithEvent(
            action = { sortCategoryUseCase(sorted) },
            successMessage = "Sorted"
        )
    }

    fun deleteCategory(id: String) {
        launchWithEvent(
            action = { deleteCategoryUseCase(id) },
            successMessage = "Deleted"
        )
    }

    fun syncCategory(workerTag: String = "sync_category") {
        launchSyncWithEvent(
            action = { syncCategoryUseCase(workerTag) },
            workerTag = workerTag,
            successMessage = "Category Synced"
        )
    }
}
