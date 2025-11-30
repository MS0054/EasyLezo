package com.appricut.easylezo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.appricut.easylezo.ui.theme.EasyLezoTheme
import kotlinx.coroutines.delay

class AcSplash : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EasyLezoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SplashPage(innerPadding)
                }
            }
        }
    }
}

@Composable
fun SplashPage(innerPadding: PaddingValues) {
    val mContext = LocalContext.current
    val activity = mContext as? Activity
    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)){
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
    }
        LaunchedEffect(UInt) {
            delay(2000)
            mContext.startActivity(Intent(mContext, MainActivity::class.java))
            activity?.finish()
        }
}