package am.mojtaba.armengo.admin.ui.screen.user

import androidx.lifecycle.viewModelScope
import am.mojtaba.armengo.admin.ui.UiState
import am.mojtaba.armengo.admin.ui.screen.BaseViewModel
import am.mojtaba.armengo.core.domain.model.User
import am.mojtaba.armengo.core.domain.usecase.category.GetUsersUseCase
import am.mojtaba.armengo.core.domain.usecase.category.SearchUserUseCase
import am.mojtaba.armengo.core.domain.usecase.category.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserV @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val searchUserUseCase: SearchUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseViewModel() {


    private val _usersUiState = MutableStateFlow(UiState<List<User>>())
    val usersUiState: StateFlow<UiState<List<User>>> = _usersUiState.asStateFlow()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users


    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            getUsersUseCase()
                .onStart {
                    _usersUiState.value = UiState(isLoading = true)
                }
                .catch { e ->
                    _usersUiState.value = UiState(error = e.message ?: "Unknown error")
                }
                .collect { users ->
                    _usersUiState.value = UiState(data = users)
                }
        }
    }

    fun searchUser(name: String = "", email: String = "") {
        launchWithEvent(
            action = {
                val result = searchUserUseCase(name, email)
                _usersUiState.value = UiState(data = result) },
            successMessage = "Searched"
        )
    }

    fun updateUser(user: User) {
        launchWithEvent(
            action = { updateUserUseCase(user) },
            successMessage = "Updated"
        )
    }
}