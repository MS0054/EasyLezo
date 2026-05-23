package am.mojtaba.armengo.admin.ui.screen.user


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import am.mojtaba.armengo.core.domain.model.User
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UserS(
    userV: UserV,
    onEdit: (User) -> Unit
) {
    val usersUiState by userV.usersUiState.collectAsState()
    var search by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var showSearchByMenu by remember { mutableStateOf(false) }
    var searchBy by remember { mutableStateOf("Name") }
    when {
        usersUiState.isLoading -> CircularProgressIndicator()
        usersUiState.error != null -> {
            Text("Error: ${usersUiState.error}", color = MaterialTheme.colorScheme.error)
        }

        else -> {
            val users = usersUiState.data ?: emptyList()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Row {
                    OutlinedTextField(
                        value = search,
                        onValueChange = { search = it },
                        label = { Text("Search by $searchBy") },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(25.dp),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    when (searchBy) {
                                        "Name" -> userV.searchUser(name = search)
                                        "Email" -> userV.searchUser(email = search)
                                    }
                                    keyboardController?.hide()
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Icon",
                                    tint = if (search.isNotBlank()) MaterialTheme.colorScheme.primary else Color.Gray
                                )
                            }
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    showSearchByMenu = true
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "Search Icon",
                                    tint = if (search.isNotBlank()) MaterialTheme.colorScheme.primary
                                    else Color.Gray
                                )
                            }

//                    IconButton(onClick = {
//                        userV.searchUser(search)
//                    }) {
//                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
//                    }
                        }
                    )
                }

                DropdownMenu(
                    expanded = showSearchByMenu,
                    shape = RoundedCornerShape(16.dp),
                    onDismissRequest = { showSearchByMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Name") },
                        onClick = { showSearchByMenu = false; searchBy = "Name" })
                    DropdownMenuItem(
                        text = { Text("Email") },
                        onClick = { showSearchByMenu = false; searchBy = "Email" })
                }
                Spacer( modifier = Modifier.padding(20.dp) )
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(users) { user ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .combinedClickable(
                                    onClick = {

                                    },
                                    onLongClick = {
                                        onEdit(user)
                                    }
                                )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    user.displayName,
                                    fontSize = 16.sp,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}


