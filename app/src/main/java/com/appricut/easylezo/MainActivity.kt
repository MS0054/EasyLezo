package com.appricut.easylezo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.appricut.easylezo.ui.theme.EasyLezoTheme
import com.appricut.easylezo.viewModel.MainViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.appricut.easylezo.Repo.FirestoreRepository
import com.appricut.easylezo.data.Sentence
import kotlinx.coroutines.launch
import kotlin.math.round


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EasyLezoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val sentences by mainViewModel.sentences.collectAsState()
    val coroutineScope = rememberCoroutineScope()


    if (selectedCategory == null) {
        // نمایش لیست Categories
        CategoryListScreen(viewModel = mainViewModel, onCategoryClick = { categoryId ->
            // وقتی روی یک کتگوری کلیک شد، جملات رو دریافت کن
            coroutineScope.launch {
                mainViewModel.fetchSentences(categoryId)
                selectedCategory = categoryId
            }
        })
    } else {
        // نمایش جملات برای کتگوری انتخاب شده
        SentenceListScreen(sentences = sentences, onBackClicked = {
            selectedCategory = null
        })
    }
}

@Composable
fun CategoryListScreen(viewModel: MainViewModel, onCategoryClick: (String) -> Unit) {
    val categories by viewModel.categories.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(categories) { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCategoryClick(category.id) }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = category.image,
                    contentDescription = category.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)

                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = category.name, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}


@Composable
fun SentenceListScreen(sentences: List<Sentence>, onBackClicked: () -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp,8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier.size(60.dp),
                onClick = {
                    onBackClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                )
            }

            IconButton(
                modifier = Modifier.size(60.dp),
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                )
            }
        }


        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(sentences) { sentence ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    AsyncImage(
                        model = sentence.image,
                        contentDescription = sentence.ar,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Text(
                    text = sentence.ar,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.fillMaxWidth().padding(24.dp)
                )
                Text(
                    text = sentence.fa,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth().padding(24.dp)
                )
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EasyLezoTheme {
        MainScreen()
    }
}