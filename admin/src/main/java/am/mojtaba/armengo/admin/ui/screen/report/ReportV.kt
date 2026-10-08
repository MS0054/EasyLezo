package am.mojtaba.armengo.admin.ui.screen.report

import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.core.domain.model.Report
import am.mojtaba.armengo.core.domain.model.ReportStatusType
import am.mojtaba.armengo.core.domain.usecase.metadata.GetMetadataReportStatusTypesUseCase
import am.mojtaba.armengo.core.domain.usecase.report.AddReportUseCase
import am.mojtaba.armengo.core.domain.usecase.report.DeleteReportUseCase
import am.mojtaba.armengo.core.domain.usecase.report.GetReportsUseCase
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
    private val getReportsUseCase: GetReportsUseCase,
    private val getMetadataReportStatusTypesUseCase: GetMetadataReportStatusTypesUseCase,
    private val addReportUseCase: AddReportUseCase,
    private val deleteReportUseCase: DeleteReportUseCase
) : BaseViewModel() {

    private val _reportsUiState = MutableStateFlow(UiState<List<Report>>())
    val reportsUiState: StateFlow<UiState<List<Report>>> = _reportsUiState.asStateFlow()

    private val _reportStatusTypesState = MutableStateFlow<List<ReportStatusType>>(emptyList())
    val reportStatusTypesState: StateFlow<List<ReportStatusType>> = _reportStatusTypesState.asStateFlow()

    init {
        getReports()
        getReportStatusTypes()
    }

    private fun getReports() {
        viewModelScope.launch {
            getReportsUseCase()
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

    private fun getReportStatusTypes() {
        viewModelScope.launch {
            getMetadataReportStatusTypesUseCase()
                .catch { }
                .collect { statusTypes ->
                    _reportStatusTypesState.value = statusTypes
                }
        }
    }

    fun addReport(report: Report) {
        launchWithEvent(
            action = {
                val res = addReportUseCase(report)
                getReports()
                res
            },
            successMessage = "Added"
        )
    }

    fun editReport(report: Report) {
        launchWithEvent(
            action = {
                val res = addReportUseCase(report)
                getReports()
                res
            },
            successMessage = "Updated"
        )
    }

    fun deleteReport(report: Report) {
        launchWithEvent(
            action = {
                val res = deleteReportUseCase(report.id)
                getReports()
                res
            },
            successMessage = "Deleted"
        )
    }
}
