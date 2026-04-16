package com.appricut.easylezo.admin.ui

data class UiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

fun <T> UiState<T>.toLoading() = copy(isLoading = true, error = null)
fun <T> UiState<T>.toSuccess(data: T) = copy(isLoading = false, data = data, error = null)
fun <T> UiState<T>.toError(message: String) = copy(isLoading = false, error = message)