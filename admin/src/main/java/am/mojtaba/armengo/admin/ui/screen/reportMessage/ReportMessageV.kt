package am.mojtaba.armengo.admin.ui.screen.reportMessage

import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.core.domain.model.ReportMessage
import am.mojtaba.armengo.core.domain.model.ReportType
import am.mojtaba.armengo.core.domain.usecase.metadata.AddMetadataReportMessageUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.DeleteMetadataReportMessageUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.GetMetadataReportMessagesUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.GetMetadataReportTypesUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.UpdateMetadataReportMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportMessageV @Inject constructor(
    private val getMetadataReportMessagesUseCase: GetMetadataReportMessagesUseCase,
    private val getMetadataReportTypesUseCase: GetMetadataReportTypesUseCase,
    private val addMetadataReportMessageUseCase: AddMetadataReportMessageUseCase,
    private val updateMetadataReportMessageUseCase: UpdateMetadataReportMessageUseCase,
    private val deleteMetadataReportMessageUseCase: DeleteMetadataReportMessageUseCase
) : BaseViewModel() {

    private val _reportMessagesUiState = MutableStateFlow(UiState<List<ReportMessage>>())
    val reportMessagesUiState: StateFlow<UiState<List<ReportMessage>>> = _reportMessagesUiState.asStateFlow()

    private val _reportTypesState = MutableStateFlow<List<ReportType>>(emptyList())
    val reportTypesState: StateFlow<List<ReportType>> = _reportTypesState.asStateFlow()

    init {
        getReportMessages()
        getReportTypes()
    }

    private fun getReportMessages() {
        viewModelScope.launch {
            getMetadataReportMessagesUseCase()
                .onStart {
                    _reportMessagesUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _reportMessagesUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { reportMessages ->
                    _reportMessagesUiState.value = UiState(data = reportMessages)
                }
        }
    }

    private fun getReportTypes() {
        viewModelScope.launch {
            getMetadataReportTypesUseCase()
                .catch { }
                .collect { reportTypes ->
                    _reportTypesState.value = reportTypes
                }
        }
    }

    fun addReportMessage(reportMessage: ReportMessage) {
        launchWithEvent(
            action = { addMetadataReportMessageUseCase(reportMessage) },
            successMessage = "Added"
        )
    }

    fun editReportMessage(reportMessage: ReportMessage) {
        launchWithEvent(
            action = { updateMetadataReportMessageUseCase(reportMessage) },
            successMessage = "Updated"
        )
    }

    fun deleteReportMessage(reportMessage: ReportMessage) {
        launchWithEvent(
            action = { deleteMetadataReportMessageUseCase(reportMessage) },
            successMessage = "Deleted"
        )
    }
}
