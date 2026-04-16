package com.appricut.easylezo.admin.ui.screen.resource

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.admin.ui.UiEvent
import com.appricut.easylezo.admin.ui.UiState
import com.appricut.easylezo.admin.ui.screen.BaseViewModel
import com.appricut.easylezo.core.domain.model.Resource
import com.appricut.easylezo.core.domain.usecase.appLanguages.GetMetadataResourcesUseCase
import com.appricut.easylezo.core.domain.usecase.category.AddCategoryUseCase
import com.appricut.easylezo.core.domain.usecase.category.AddMetadataResourceUseCase
import com.appricut.easylezo.core.domain.usecase.category.DeleteCategoryUseCase
import com.appricut.easylezo.core.domain.usecase.category.DeleteMetadataResourceUseCase
import com.appricut.easylezo.core.domain.usecase.category.UpdateMetadataResourceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResourceV @Inject constructor(
    private val getMetadataResourcesUseCase: GetMetadataResourcesUseCase,
    private val addMetadataResourceUseCase: AddMetadataResourceUseCase,
    private val updateMetadataResourceUseCase: UpdateMetadataResourceUseCase,
    private val deleteMetadataResourceUseCase: DeleteMetadataResourceUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,

) : BaseViewModel() {



    private val _resourceUiState = MutableStateFlow(UiState<List<Resource>>())
    val resourcesUiState: StateFlow<UiState<List<Resource>>> = _resourceUiState.asStateFlow()

    init {
        getResource()
    }


    private fun getResource() {
        viewModelScope.launch {
            getMetadataResourcesUseCase()
                .onStart {
                    _resourceUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _resourceUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { resources ->
                    _resourceUiState.value = UiState(data = resources)
                }
        }
    }

    fun addResource(context: Context, resource: Resource) {
        launchWithEvent(
            action = { addMetadataResourceUseCase(context,resource) },
            successMessage = "Added"
        )
    }

    fun editResource(resource: Resource) {
        launchWithEvent(
            action = { updateMetadataResourceUseCase(resource) },
            successMessage = "Updated"
        )
    }

    fun deleteResource(resource: Resource) {
        launchWithEvent(
            action = { deleteMetadataResourceUseCase(resource) },
            successMessage = "Deleted"
        )
    }

}
