package com.appricut.easylezo.admin.ui.sheet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SheetV @Inject constructor() : ViewModel() {

    var currentSheet by mutableStateOf<AppSheet>(AppSheet.None)
        private set

    fun openSheet(sheet: AppSheet) { currentSheet = sheet }
    fun closeSheet() { currentSheet = AppSheet.None }
}