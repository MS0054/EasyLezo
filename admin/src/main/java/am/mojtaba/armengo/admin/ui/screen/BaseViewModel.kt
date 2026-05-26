package am.mojtaba.armengo.admin.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.admin.ui.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()

    protected fun launchWithEvent(
        successMessage: String,
        action: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                _event.emit(UiEvent.Started)
                action()
                _event.emit(UiEvent.Success(successMessage))
            } catch (e: Exception) {
                _event.emit(UiEvent.Error(e.message ?: "خطایی رخ داد"))
            }
        }
    }

    protected fun isSyncNeeded(isSyncNeeded: Boolean) {
        viewModelScope.launch {
            _event.emit(UiEvent.SyncStatue(isSyncNeeded))
        }
    }

    protected fun launchSyncWithEvent(
        successMessage: String,
        workerTag: String,
        action: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                _event.emit(UiEvent.StartSync(workerTag))
                action()
                _event.emit(UiEvent.Success(successMessage))
            } catch (e: Exception) {
                _event.emit(UiEvent.Error(e.message ?: "خطایی رخ داد"))
            }
        }
    }
}