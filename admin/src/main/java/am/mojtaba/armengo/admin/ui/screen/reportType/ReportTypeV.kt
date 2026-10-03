package am.mojtaba.armengo.admin.ui.screen.reportType

import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.core.domain.model.ReportType
import am.mojtaba.armengo.core.domain.usecase.metadata.AddMetadataReportTypeUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.DeleteMetadataReportTypeUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.GetMetadataReportTypesUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.UpdateMetadataReportTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportTypeV @Inject constructor(
    private val getMetadataReportTypesUseCase: GetMetadataReportTypesUseCase,
    private val addMetadataReportTypeUseCase: AddMetadataReportTypeUseCase,
    private val updateMetadataReportTypeUseCase: UpdateMetadataReportTypeUseCase,
    private val deleteMetadataReportTypeUseCase: DeleteMetadataReportTypeUseCase
) : BaseViewModel() {

    private val _reportTypesUiState = MutableStateFlow(UiState<List<ReportType>>())
    val reportTypesUiState: StateFlow<UiState<List<ReportType>>> = _reportTypesUiState.asStateFlow()

    init {
        getReportType()
    }

    private fun getReportType() {
        viewModelScope.launch {
            getMetadataReportTypesUseCase()
                .onStart {
                    _reportTypesUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _reportTypesUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { reportTypes ->
                    _reportTypesUiState.value = UiState(data = reportTypes)
                }
        }
    }

    fun addReportType(reportType: ReportType) {
        launchWithEvent(
            action = { addMetadataReportTypeUseCase(reportType) },
            successMessage = "Added"
        )
    }

    fun editReportType(reportType: ReportType) {
        launchWithEvent(
            action = { updateMetadataReportTypeUseCase(reportType) },
            successMessage = "Updated"
        )
    }

    fun deleteReportType(reportType: ReportType) {
        launchWithEvent(
            action = { deleteMetadataReportTypeUseCase(reportType) },
            successMessage = "Deleted"
        )
    }
}
