package am.mojtaba.armengo.admin.ui.screen.error

import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.core.domain.model.Error
import am.mojtaba.armengo.core.domain.usecase.appLanguages.GetMetadataErrorsUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.AddMetadataErrorUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.DeleteMetadataErrorUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.UpdateMetadataErrorUseCase
import android.content.Context
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ErrorV @Inject constructor(
    private val getMetadataErrorsUseCase: GetMetadataErrorsUseCase,
    private val addMetadataErrorUseCase: AddMetadataErrorUseCase,
    private val updateMetadataErrorUseCase: UpdateMetadataErrorUseCase,
    private val deleteMetadataErrorUseCase: DeleteMetadataErrorUseCase,
    ) : BaseViewModel() {


    private val _errorUiState = MutableStateFlow(UiState<List<Error>>())
    val errorUiState: StateFlow<UiState<List<Error>>> = _errorUiState.asStateFlow()


    init {
        getErrors()
    }


    private fun getErrors() {
        viewModelScope.launch {
            getMetadataErrorsUseCase()
                .onStart {
                    _errorUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _errorUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { errors ->
                    _errorUiState.value = UiState(data = errors)
                }
        }
    }

    fun addError(error: Error) {
        launchWithEvent(
            action = { addMetadataErrorUseCase(error) },
            successMessage = "Added"
        )
    }

    fun editError(error: Error) {
        launchWithEvent(
            action = { updateMetadataErrorUseCase(error) },
            successMessage = "Updated"
        )
    }

    fun deleteError(error: Error) {
        launchWithEvent(
            action = { deleteMetadataErrorUseCase(error) },
            successMessage = "Deleted"
        )
    }

}