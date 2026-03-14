package com.appricut.easylezo.ui.screen.user

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.appricut.easylezo.domain.model.Category
import com.appricut.easylezo.ui.component.LanguageAwareText
import com.appricut.easylezo.ui.screen.language.sheet.AppLanguageSheet

@Composable
fun CategoryListScreen(
    categoryViewModel: CategoryViewModel,
    onCategorySelected: (Category) -> Unit,
    onProfileSelected: () -> Unit
) {
    val categoriesUiState by categoryViewModel.categoryUiState.collectAsState()
    val appLanguageUiState by categoryViewModel.appLanguagesUiState.collectAsState()
    var showSelectLanguageSheet by remember { mutableStateOf(false) }

    if (showSelectLanguageSheet) {
        AppLanguageSheet(
            uiState = appLanguageUiState,
            onLanguageSelected = { newLanguage ->
                categoryViewModel.updateUserAppLanguages(appLanguageUiState.data, newLanguage)
            },
            onDismiss = { showSelectLanguageSheet = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- Header ---
        HeaderSection(
            onSettingsClick = { showSelectLanguageSheet = true },
            onProfileClick = onProfileSelected
        )

        Spacer(Modifier.height(24.dp))

        // --- Content Area ---
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                // نمایش اسکلتون در حالت لودینگ
                categoriesUiState.isLoading -> {
                    SkeletonGrid()
                }

                // نمایش خطا
                categoriesUiState.error != null -> {
                    Text(
                        text = "Error: ${categoriesUiState.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // نمایش لیست داده‌ها
                else -> {
                    val categories = categoriesUiState.data ?: emptyList()
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(categories) { cat ->
                            CategoryCard(
                                category = cat,
                                onCardClick = {
                                    onCategorySelected(cat)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- اجزای کمکی برای تمیزی کد ---

@Composable
fun HeaderSection(onSettingsClick: () -> Unit, onProfileClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = onSettingsClick) {
            Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
        }
        LanguageAwareText("L I N G O", style = MaterialTheme.typography.headlineMedium)
        IconButton(onClick = onProfileClick) {
            Icon(imageVector = Icons.Filled.Person, contentDescription = "Profile")
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
                modifier = Modifier.fillMaxWidth().height(64.dp),
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
                        .background(brush, RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp))
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
            modifier = Modifier.defaultMinSize(minHeight = 200.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (category.image.isNotEmpty()) {
                AsyncImage(
                    model = category.image,
                    contentDescription = category.name,
                    modifier = Modifier.size(180.dp).padding(16.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().height(64.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LanguageAwareText(
                    text = category.name,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.headlineLarge
                )
                Box(
                    modifier = Modifier
                        .size(56.dp, 64.dp)
                        .background(
                            MaterialTheme.colorScheme.background,
                            RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp)
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