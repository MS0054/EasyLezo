package am.mojtaba.armengo.admin.ui.screen.splash
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen(
    splashV: SplashV,
    onNavigate: (String) -> Unit
) {
    // گرفتن وضعیت لودینگ و مسیر صفحه از ویومدل
    val isLoading by splashV.isLoading.collectAsState()
    val targetScreen by splashV.screen.collectAsState()

    // اجرای عملیات فقط برای یک بار در شروع صفحه
    LaunchedEffect(Unit) {
        splashV.start()
    }

    // گوش دادن به تغییرات targetScreen برای جابه‌جایی
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
            // نمایش آیکون برنامه
            Icon(
                painter = painterResource(android.R.mipmap.sym_def_app_icon),
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp)
            )

            // اگر هنوز در حال لودینگ بود، یک چرخنده نمایش بده
            if (isLoading) {
                Spacer(modifier = Modifier.height(24.dp))
                CircularProgressIndicator()
            }
        }
    }
}