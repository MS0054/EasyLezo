package am.mojtaba.armengo.ui.screen.splash

import android.content.Intent
import android.graphics.BlurMaskFilter
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.UpdateType
import am.mojtaba.armengo.ui.Screen
import am.mojtaba.armengo.ui.screen.splash.sheet.UpdateBottomSheet
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import am.mojtaba.armengo.app.R
import am.mojtaba.armengo.core.domain.model.UpdateResult
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    val updateState by splashViewModel.updateState.collectAsState()
    var showSheet by remember { mutableStateOf(true) }
    var updateResult by remember { mutableStateOf<UpdateResult?>(null) }

//    LaunchedEffect(Unit) {
//        splashViewModel.start(context)
//    }
//    LaunchedEffect(Unit) {
//        splashViewModel.screen.filterNotNull().first().let { onNavigate(it.route) }
//    }

    when (val state = updateState) {
        is UpdateStatus.Success -> {
            when (state.updateResult.type) {
                UpdateType.OPTIONAL-> {
                    updateResult = state.updateResult
                    showSheet = true

                }
                UpdateType.FORCE -> {
                    updateResult = state.updateResult
                    showSheet = true
                }
                UpdateType.NONE -> {

                    LaunchedEffect(Unit) {
                        delay(200)
                        onNavigate(Screen.Category.route)
                    }
                }
            }
        }
        is UpdateStatus.Error -> {
            LaunchedEffect(Unit) { onNavigate(Screen.Category.route) }
        }
        UpdateStatus.Idle -> {
            Log.i("XoXo", "Idle")
            // نمایش لودینگ کوچک اگر نیاز بود
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        AnimatedSplashScreen(R.mipmap.ic_launcher)
        AdvancedSplashAnimation(R.mipmap.ic_launcher)

//        AsyncImage(
//            model = R.mipmap.ic_launcher,
//            modifier = Modifier.size(156.dp),
//            contentDescription = null,
//        )
    }


    if (showSheet) {
        updateResult?.let {
            UpdateBottomSheet(
                it ,
                onUpdateClick = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                },
                onDismiss = {
                    showSheet = false
                    onNavigate(Screen.Category.route)
                }
            )
        }
    }
}

@Composable
fun AdvancedSplashAnimation(logoRes: Int) {
    var startAnimation by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition(label = "ShadowMovement")

    // انیمیشن اصلی کوچک شدن لوگو
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 0.7f else 1f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "LogoScale"
    )

    // انیمیشن چرخش و جابجایی نامنظم سایه‌ها
    val shadowRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(5000, easing = LinearEasing)),
        label = "ShadowRotation"
    )
// ۴. انیمیشن جابجایی (Offset) سایه
    val shadowOffset by animateDpAsState(
        targetValue = if (startAnimation) 30.dp else 0.dp,
        animationSpec = tween(2000),
        label = "ShadowOffset"
    )

//    val shadowOffset by infiniteTransition.animateValue(
//        initialValue = Offset(-20f, -20f),
//        targetValue = Offset(20f, 20f),
//        typeConverter = Offset.VectorConverter,
//        animationSpec = infiniteRepeatable(
//            animation = tween(200, easing = LinearOutSlowInEasing),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "ShadowOffset"
//    )

    LaunchedEffect(Unit) { startAnimation = true }

    Box(

        contentAlignment = Alignment.Center
    ) {
        // --- لایه سایه‌های بسیار غلیظ و نامنظم ---
        if (startAnimation) {
            Box(
                modifier = Modifier
                    .size(250.dp)
//                    .offset(x = shadowOffset.x.dp, y = shadowOffset.y.dp)
                    .offset(x = shadowOffset, y = shadowOffset)
                    .rotate(shadowRotation)
                    .blur(60.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF6200EE).copy(alpha = 0.8f), // رنگ اصلی لوگو
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )

            Box(
                modifier = Modifier
                    .size(220.dp)
//                    .offset(x = -shadowOffset.x.dp, y = shadowOffset.y.dp)
                    .offset(x = -shadowOffset, y = shadowOffset)
                    .rotate(-shadowRotation * 1.5f) // چرخش در جهت مخالف
                    .blur(80.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF03DAC6).copy(alpha = 0.6f), // رنگ مکمل
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )
        }

        // --- آیکن اصلی ---
        AsyncImage(
            model = logoRes,
            contentDescription = "Logo",
            modifier = Modifier
                .size(180.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
        )
    }
}

@Composable
fun AnimatedSplashScreen(logoRes: Int) {
    // 1. مدیریت وضعیت انیمیشن
    var startAnimation by remember { mutableStateOf(false) }

    // انیمیشن تغییر اندازه از 1f به 0.5f
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 0.5f else 1f,
        animationSpec = tween (
            durationMillis = 1500, // مدت زمان انیمیشن
            easing = FastOutSlowInEasing
        ),
        label = "LogoScale"
    )

    // انیمیشن شدت سایه (Opacity) که همزمان با کوچک شدن زیاد می‌شود
    val shadowAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 0.6f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = "ShadowAlpha"
    )

    // شروع انیمیشن به محض لود شدن صفحه
    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model =  logoRes,
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .drawBehind {
                    // رسم سایه نرم در اطراف لوگو
                    if (shadowAlpha > 0f) {
                        drawIntoCanvas { canvas ->
                            val paint = Paint()
                            val frameworkPaint = paint.asFrameworkPaint()

                            // تنظیم رنگ سایه (می‌توانید رنگ اصلی لوگو را اینجا بگذارید)
                            val shadowColor = Color(0xFF6200EE)

                            frameworkPaint.color = shadowColor.copy(alpha = shadowAlpha).toArgb()

                            // ایجاد حالت بلور (Blur) برای سایه
                            frameworkPaint.maskFilter = BlurMaskFilter(
                                60f * (1 - scale + 0.5f), // هرچه کوچک‌تر شود سایه پخش‌تر می‌شود
                                BlurMaskFilter.Blur.NORMAL
                            )

                            canvas.drawCircle(
                                center = center,
                                radius = size.width / 2,
                                paint = paint
                            )
                        }
                    }
                }
        )
    }
}
