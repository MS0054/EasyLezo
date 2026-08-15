package am.mojtaba.armengo.ui.screen.category

import am.mojtaba.armengo.app.R
import am.mojtaba.armengo.core.domain.model.Category
import am.mojtaba.armengo.ui.component.LanguageAwareText
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    uiState: CategoryUiState,
    onCategorySelected: (Category) -> Unit,
    onProfileSelected: () -> Unit,
    onLanguageClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        when {
            uiState.isLoading -> {
                SkeletonGrid()
            }

            else -> {
                LazyVerticalGrid(
                    contentPadding = PaddingValues(top = 120.dp, bottom = 360.dp),
                    columns = GridCells.Fixed(2)
                ) {
                    items(uiState.categories) { category ->
                        CategoryCard(
                            category = category,
                            onCardClick = {
                                onCategorySelected(category)
                            }
                        )
                    }
                }
            }
        }
        HeaderSection(
            onSettingsClick = onLanguageClick,
            onProfileClick = onProfileSelected
        )
    }
}

@Composable
fun HeaderSection(onSettingsClick: () -> Unit, onProfileClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .size(140.dp)
            .background(
                brush = Brush.verticalGradient(
                    startY = 0f,
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surface.copy(.8f),
                        MaterialTheme.colorScheme.surface.copy(.5f),
                        Color.Transparent
                    )
                )
            )
    ) {
        IconButton(onClick = onSettingsClick) {
            Icon(
                painter = painterResource(R.drawable.outline_translate_24),
                contentDescription = "Settings"
            )
        }
        Row {
            SmoothStaggeredTextVertical(text = "A R M E N", initialDelay = 0L)
            SmoothStaggeredTextVertical(text = " G O", initialDelay = 1000L)
        }


        IconButton(onClick = onProfileClick) {
            Icon(imageVector = Icons.Filled.Settings, contentDescription = "")
        }
    }
}

@Composable
fun SmoothStaggeredTextVertical(text: String, initialDelay: Long) {
    val characters = remember(text) { text.map { it.toString() } }

    Row {
        characters.forEachIndexed { index, char ->
            val animatableY = remember { Animatable(100f) }
            val opacity = remember { Animatable(0f) }

            LaunchedEffect(text) {
                delay(initialDelay + (index * 70L))

                launch {
                    animatableY.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(
                            durationMillis = 800,
                            // Using the same professional Ease for the stretch mode when reaching the top
                            easing = CubicBezierEasing(0.2f, 1.4f, 0.3f, 1f)
                        )
                    )
                }
                launch {
                    opacity.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(600)
                    )
                }
            }

            LanguageAwareText(
                text = char,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.graphicsLayer {
                    translationY = animatableY.value // Change to Y axis
                    alpha = opacity.value
                }
            )
        }
    }
}

@Composable
fun SmoothStaggeredText(text: String, initialDelay: Long) {
    val characters = remember(text) { text.map { it.toString() } }

    Row {
        characters.forEachIndexed { index, char ->
            val animatableX = remember { Animatable(500f) } // شروع از سمت راست
            val opacity = remember { Animatable(0f) }

            LaunchedEffect(text) {
                // تأخیر کلی کلمه + تأخیر پله‌ای هر حرف
                delay(initialDelay + (index * 60L))

                launch {
                    animatableX.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(
                            durationMillis = 800,
                            easing = CubicBezierEasing(0.2f, 1.3f, 0.3f, 1f)
                        )
                    )
                }
                launch {
                    opacity.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(600)
                    )
                }
            }

            LanguageAwareText(
                text = char,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.graphicsLayer {
                    translationX = animatableX.value
                    alpha = opacity.value
                }
            )
        }
    }
}

@Composable
fun SkeletonGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = false // در حالت لودینگ اسکرول نشود بهتر است
    ) {
        items(6) { // نمایش ۶ کارت فرضی به عنوان اسکلتون
            SkeletonCategoryCard()
        }
    }
}

@Composable
fun SkeletonCategoryCard() {
    // ایجاد انیمیشن Shimmer
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim, y = translateAnim)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column(
            modifier = Modifier.defaultMinSize(minHeight = 200.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // شبیه‌ساز تصویر
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(brush)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // شبیه‌ساز متن
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(80.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )

                // شبیه‌ساز دکمه
                Box(
                    modifier = Modifier
                        .size(56.dp, 64.dp)
                        .background(
                            brush,
                            RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp)
                        )
                )
            }
        }
    }
}

@Composable
fun CategoryCard(category: Category, onCardClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable { onCardClick() }
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (category.image.isNotEmpty()) {
                AsyncImage(
                    model = category.image,
                    contentDescription = null,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 0.dp),

                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    LanguageAwareText(
                        text = category.fromText,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                    LanguageAwareText(
                        text = category.toText,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }

                Box(
                    modifier = Modifier
                        .size(52.dp, 56.dp)
                        .background(
                            MaterialTheme.colorScheme.background,
                            RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}