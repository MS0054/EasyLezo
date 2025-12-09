package com.appricut.easylezo.ui.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.appricut.easylezo.ui.screen.category.CategoryListScreen
import com.appricut.easylezo.ui.screen.main.MainViewModel
import com.appricut.easylezo.ui.screen.sentence.SentenceListScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun MainScreen() {

    val auth = Firebase.auth
    var isAdmin by remember { mutableStateOf<Boolean?>(null) }

    // گرفتن نقش ادمین فقط 1 بار
    LaunchedEffect(Unit) {
        auth.currentUser?.getIdToken(true)
            ?.addOnSuccessListener { result ->
                val admin = result.claims["admin"] as? Boolean ?: false
                isAdmin = admin
            }
            ?.addOnFailureListener {
                isAdmin = false
            }
    }

    when (isAdmin) {
        null -> {
            // صفحه لودینگ
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        true -> {
            AdminScreen()    // صفحه ادمین
        }

        false -> {
            UserMainScreen() // صفحه عادی
        }
    }


}

@Composable
fun AdminScreen(){
    Text("this is admin page")
//    val authRepo = AuthRepository()
//    AuthScreen(authRepo)
}

@Composable
fun UserMainScreen(viewModel: MainViewModel = hiltViewModel()){

    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val categories by viewModel.categories.collectAsState()
    val sentences by viewModel.sentences.collectAsState()

    LaunchedEffect(Unit) {
//        viewModel.fetchCategories()
    }

    if (selectedCategory == null) {

        CategoryListScreen(
            categories = categories,
            onClick = { id ->
                selectedCategory = id
                viewModel.fetchSentences(id)
            }
        )

    } else {

        SentenceListScreen(
            sentences = sentences,
            onBack = { selectedCategory = null }
        )
    }

}
