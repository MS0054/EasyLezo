package am.mojtaba.armengo.admin.ui.screen.category

import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.core.domain.model.Category
import am.mojtaba.armengo.core.domain.usecase.category.AddCategoryUseCase
import am.mojtaba.armengo.core.domain.usecase.category.ObserveUnSyncedCategoryUseCase
import am.mojtaba.armengo.core.domain.usecase.category.DeleteCategoryUseCase
import am.mojtaba.armengo.core.domain.usecase.category.GetCategoriesUseCase
import am.mojtaba.armengo.core.domain.usecase.category.SortCategoryUseCase
import am.mojtaba.armengo.core.domain.usecase.category.SyncCategoryFromServerUseCase
import am.mojtaba.armengo.core.domain.usecase.category.SyncCategoryToServerUseCase
import am.mojtaba.armengo.core.domain.usecase.category.UpdateCategoryUseCase
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
    private val syncCategoryToServerUseCase: SyncCategoryToServerUseCase,
    private val syncCategoryFromServerUseCase: SyncCategoryFromServerUseCase,
    private val observeUnSyncedCategoryUseCase: ObserveUnSyncedCategoryUseCase
) : BaseViewModel() {


    private val _categoryUiState = MutableStateFlow(UiState<List<Category>>())
    val categoryUiState: StateFlow<UiState<List<Category>>> = _categoryUiState.asStateFlow()


    private val _unsyncedCategoryUiState = MutableStateFlow(UiState<Boolean>())
    val unsyncedCategoryUiState: StateFlow<UiState<Boolean>> = _unsyncedCategoryUiState.asStateFlow()

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
                    isExistUnSyncedCategory()
                }
        }
    }

    fun isExistUnSyncedCategory(){
        viewModelScope.launch {
            observeUnSyncedCategoryUseCase().collect { isSyncNeeded ->
                isSyncNeeded(isSyncNeeded)
                _unsyncedCategoryUiState.value = UiState(data = isSyncNeeded)
            }
        }
    }
    fun syncCategoryToServer(workerTag: String = "sync_category") {
        launchSyncWithEvent(
            action = { syncCategoryToServerUseCase(workerTag) },
            workerTag = workerTag,
            successMessage = "Category Synced To Server"
        )
    }

    fun rejectCategoryChanges() {
        launchWithEvent(
            action = { syncCategoryFromServerUseCase(true) },
            successMessage = "Rejected"
        )
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


}
