package am.mojtaba.armengo.admin.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun AuthScreen(
    authV: AuthV,
    onSuccess: () -> Unit
) {
    val state by authV.uiState.collectAsState()
    var email by remember { mutableStateOf("mojtaabaa@gmail.com") }
    var pass by remember { mutableStateOf("151515") }
    var display by remember { mutableStateOf("") }
    var isSignUp by remember { mutableStateOf(false) }
    val context = LocalContext.current

//    LaunchedEffect(state) {
        when (state) {
            is AuthUiState.Loading ->{
                CircularProgressIndicator()
            }
            is AuthUiState.Success -> {
                onSuccess()
            }
            is AuthUiState.Error -> {
                Toast.makeText(context, (state as AuthUiState.Error).message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
//    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text(if (isSignUp) "Sign Up" else "Sign In", style = MaterialTheme.typography.headlineMedium)
        if (isSignUp) {
            OutlinedTextField(value = display, onValueChange = { display = it }, label = { Text("Display name") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
        }
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            if (isSignUp) authV.signUp(email, pass, display) else authV.signIn(email, pass)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(if (isSignUp) "Sign Up" else "Sign In")
        }
        Spacer(Modifier.height(8.dp))
        TextButton(onClick = { isSignUp = !isSignUp }) {
            Text(if (isSignUp) "Already have account? Sign In" else "Don't have account? Sign Up")
        }
    }
}
