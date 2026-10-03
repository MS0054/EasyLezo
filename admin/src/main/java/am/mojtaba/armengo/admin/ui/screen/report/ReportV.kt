package am.mojtaba.armengo.admin.ui.screen.report

import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.core.domain.model.Report
import am.mojtaba.armengo.core.domain.usecase.metadata.AddMetadataReportUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.DeleteMetadataReportUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.GetMetadataReportsUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.UpdateMetadataReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportV @Inject constructor(
    private val getMetadataReportsUseCase: GetMetadataReportsUseCase,
    private val addMetadataReportUseCase: AddMetadataReportUseCase,
    private val updateMetadataReportUseCase: UpdateMetadataReportUseCase,
    private val deleteMetadataReportUseCase: DeleteMetadataReportUseCase
) : BaseViewModel() {

    private val _reportsUiState = MutableStateFlow(UiState<List<Report>>())
    val reportsUiState: StateFlow<UiState<List<Report>>> = _reportsUiState.asStateFlow()

    init {
        getReports()
    }

    private fun getReports() {
        viewModelScope.launch {
            getMetadataReportsUseCase()
                .onStart {
                    _reportsUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _reportsUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { reports ->
                    _reportsUiState.value = UiState(data = reports)
                }
        }
    }

    fun addReport(report: Report) {
        launchWithEvent(
            action = { addMetadataReportUseCase(report) },
            successMessage = "Added"
        )
    }

    fun editReport(report: Report) {
        launchWithEvent(
            action = { updateMetadataReportUseCase(report) },
            successMessage = "Updated"
        )
    }

    fun deleteReport(report: Report) {
        launchWithEvent(
            action = { deleteMetadataReportUseCase(report) },
            successMessage = "Deleted"
        )
    }
}
