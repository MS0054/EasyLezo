package com.appricut.easylezo.ui.screen.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appricut.easylezo.domain.model.Metadata
import com.appricut.easylezo.domain.usecase.metadata.GetMetadataUseCase
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