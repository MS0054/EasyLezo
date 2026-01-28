package com.appricut.easylezo.ui.screen.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.appricut.easylezo.data.model.Category

@Composable
fun CategoryListScreen(
    viewModel: MainViewModel,
    onCategorySelected: (Category) -> Unit,
    onProfileSelected: () -> Unit
) {
    val categories by viewModel.categories.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

            Text("Categories", style = MaterialTheme.typography.headlineMedium)

            IconButton(
                onClick = {
                    onProfileSelected()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile"
                )
            }
        }
        Spacer(Modifier.height(8.dp))

//        if (loading) CircularProgressIndicator()
        error?.let { Text("Error: $it", color = MaterialTheme.colorScheme.error) }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(categories) { cat ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        viewModel.selectCategory(cat.id)
                        onCategorySelected(cat)
                    }) {
                    Row(modifier = Modifier.padding(12.dp)) {
                        if (cat.image.isNotEmpty()) {
                            AsyncImage(model = cat.image, contentDescription = cat.name, modifier = Modifier.size(64.dp))
                            Spacer(Modifier.width(12.dp))
                        }
                        Column {
                            Text(cat.name, style = MaterialTheme.typography.titleLarge)
                            Text("ID: ${cat.id}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
