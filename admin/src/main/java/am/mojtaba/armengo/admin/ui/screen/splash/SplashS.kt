package am.mojtaba.armengo.admin.ui.screen.splash
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SplashScreen(
    splashV: SplashV,
    onNavigate: (String) -> Unit
) {
    // گرفتن وضعیت لودینگ و مسیر صفحه از ویومدل
    val isLoading by splashV.isLoading.collectAsStateWithLifecycle()
    val targetScreen by splashV.screen.collectAsStateWithLifecycle()
    val errorMessage by splashV.errorMessage.collectAsStateWithLifecycle()


    LaunchedEffect(targetScreen) {
        targetScreen?.let {
            onNavigate(it.route)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(android.R.mipmap.sym_def_app_icon),
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp)
            )

            if (isLoading) {
                Spacer(modifier = Modifier.height(24.dp))
                CircularProgressIndicator()
            }
        }

        // نمایش ارور دیالوگ در صورت بروز خطای سینک
        errorMessage?.let { error ->
            AlertDialog(
                onDismissRequest = { },
                title = { Text("خطا در همگام‌سازی") },
                text = { Text(error) },
                confirmButton = {
                    Button (onClick = { splashV.start(isForce = true) }) {
                        Text("تلاش مجدد")
                    }
                }
            )
        }

    }
}