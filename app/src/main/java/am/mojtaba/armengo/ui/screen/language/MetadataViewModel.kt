package am.mojtaba.armengo.ui.screen.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.core.domain.model.Metadata
import am.mojtaba.armengo.core.domain.usecase.metadata.GetMetadataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MetadataViewModel @Inject constructor(
    getMetadataUseCase: GetMetadataUseCase,
) : ViewModel() {
    val metadata: StateFlow<Metadata?> = getMetadataUseCase().stateIn(viewModelScope, SharingStarted.Lazily,null)
}