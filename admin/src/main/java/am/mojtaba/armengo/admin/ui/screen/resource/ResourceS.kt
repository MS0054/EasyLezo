package am.mojtaba.armengo.admin.ui.screen.resource

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import am.mojtaba.armengo.core.domain.model.Resource
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ResourceS(
    resourceV: ResourceV,
    onEdit: (Resource) -> Unit,
    onAdd: (Int) -> Unit,
    openSentences: (Resource) -> Unit
) {
    val resourcesUiState by resourceV.resourcesUiState.collectAsState()

    when {
        resourcesUiState.isLoading -> CircularProgressIndicator()
        resourcesUiState.error != null -> { Text("Error: ${resourcesUiState.error}", color = MaterialTheme.colorScheme.error) }
        else -> {
            val resources = resourcesUiState.data ?: emptyList()
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                LazyColumn (modifier = Modifier.fillMaxWidth()) {
                    items(resources) { resource ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .combinedClickable(
                                    onClick = {
//                                        openSentences(cat.id)
                                    },
                                    onLongClick = {
                                        onEdit(resource)
                                    }
                                )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    resource.name,
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


