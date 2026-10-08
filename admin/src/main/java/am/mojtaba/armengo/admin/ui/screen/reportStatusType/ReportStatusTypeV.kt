package am.mojtaba.armengo.admin.ui.screen.reportStatusType

import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.core.domain.model.ReportStatusType
import am.mojtaba.armengo.core.domain.usecase.metadata.AddMetadataReportStatusTypeUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.DeleteMetadataReportStatusTypeUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.GetMetadataReportStatusTypesUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.UpdateMetadataReportStatusTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportStatusTypeV @Inject constructor(
    private val getMetadataReportStatusTypesUseCase: GetMetadataReportStatusTypesUseCase,
    private val addMetadataReportStatusTypeUseCase: AddMetadataReportStatusTypeUseCase,
    private val updateMetadataReportStatusTypeUseCase: UpdateMetadataReportStatusTypeUseCase,
    private val deleteMetadataReportStatusTypeUseCase: DeleteMetadataReportStatusTypeUseCase
) : BaseViewModel() {

    private val _reportStatusTypesUiState = MutableStateFlow(UiState<List<ReportStatusType>>())
    val reportStatusTypesUiState: StateFlow<UiState<List<ReportStatusType>>> = _reportStatusTypesUiState.asStateFlow()

    init {
        getReportStatusTypes()
    }

    private fun getReportStatusTypes() {
        viewModelScope.launch {
            getMetadataReportStatusTypesUseCase()
                .onStart {
                    _reportStatusTypesUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _reportStatusTypesUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { reportStatusTypes ->
                    _reportStatusTypesUiState.value = UiState(data = reportStatusTypes)
                }
        }
    }

    fun addReportStatusType(reportStatusType: ReportStatusType) {
        launchWithEvent(
            action = { addMetadataReportStatusTypeUseCase(reportStatusType) },
            successMessage = "Added"
        )
    }

    fun editReportStatusType(reportStatusType: ReportStatusType) {
        launchWithEvent(
            action = { updateMetadataReportStatusTypeUseCase(reportStatusType) },
            successMessage = "Updated"
        )
    }

    fun deleteReportStatusType(reportStatusType: ReportStatusType) {
        launchWithEvent(
            action = { deleteMetadataReportStatusTypeUseCase(reportStatusType) },
            successMessage = "Deleted"
        )
    }
}
