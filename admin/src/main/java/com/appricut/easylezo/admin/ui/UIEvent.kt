package com.appricut.easylezo.admin.ui

sealed class UiEvent {
    object Started : UiEvent()
    data class Success(val message: String) : UiEvent()
    data class Error(val message: String) : UiEvent()
}