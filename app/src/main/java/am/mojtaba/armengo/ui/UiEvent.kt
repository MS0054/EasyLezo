package am.mojtaba.armengo.ui

sealed interface UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent
}