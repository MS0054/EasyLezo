package am.mojtaba.armengo.ui.screen.splash

import am.mojtaba.armengo.app.R
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun SplashScreen(
    uiState: SplashUiState,
    onRefreshClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AdvancedSplashAnimation(logoRes = R.mipmap.ic_launcher)

        if (uiState.updateStatus is UpdateStatus.Error) {
            IconButton(onClick = onRefreshClick) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    tint = Color.White,
                    contentDescription = "Retry"
                )
            }
        }
    }
}

@Composable
fun AdvancedSplashAnimation(logoRes: Int) {
    var startAnimation by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition(label = "ShadowMovement")

    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 0.7f else 1f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "LogoScale"
    )

    val shadowRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(5000, easing = LinearEasing)),
        label = "ShadowRotation"
    )

    val shadowOffset by animateDpAsState(
        targetValue = if (startAnimation) 30.dp else 0.dp,
        animationSpec = tween(2000),
        label = "ShadowOffset"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Box(contentAlignment = Alignment.Center) {
        if (startAnimation) {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .offset(x = shadowOffset, y = shadowOffset)
                    .rotate(shadowRotation)
                    .blur(60.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF6200EE).copy(alpha = 0.8f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )

            Box(
                modifier = Modifier
                    .size(220.dp)
                    .offset(x = -shadowOffset, y = shadowOffset)
                    .rotate(-shadowRotation * 1.5f)
                    .blur(80.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF03DAC6).copy(alpha = 0.6f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )
        }

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