package com.appricut.easylezo.ui.screen.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.appricut.easylezo.data.model.Category

@Composable
fun AdminScreen(viewModel: AdminViewModel) {
    val categories by viewModel.categories.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Admin Dashboard", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))

        if (loading) CircularProgressIndicator()
        error?.let { Text("Error: $it", color = MaterialTheme.colorScheme.error) }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(categories) { cat ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        selectedCategory = cat
                        viewModel.selectCategory(cat.id)
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

        selectedCategory?.let { cat ->
            Spacer(Modifier.height(12.dp))
            Text("Sentences in ${cat.name}", style = MaterialTheme.typography.titleMedium)
            // SentenceListScreen مشابه User Layer، با امکان delete/add
        }
    }
}
