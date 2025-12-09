package com.appricut.easylezo.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appricut.easylezo.ui.screen.main.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun UserHomeScreen(viewModel: MainViewModel) {
    val sentences by viewModel.sentences.collectAsState()
    LaunchedEffect(Unit) { viewModel.refreshSentences() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("User Home", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Button(onClick = { Firebase.auth.signOut() }) { Text("Logout") }
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(sentences) { s ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = s.ar, style = MaterialTheme.typography.titleLarge)
                        Text(text = s.fa, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}