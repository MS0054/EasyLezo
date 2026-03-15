package com.appricut.easylezo.ui.screen.language.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.ui.UiState
import com.appricut.easylezo.ui.component.LanguageAwareText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLanguageSheet(
    uiState: UiState<AppLanguages>,
    onLanguageSelected: (AppLanguages) -> Unit,
    onDismiss: () -> Unit
) {



    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        when {
            uiState.isLoading -> {
//                LoadingStateView()
            }

            uiState.error != null -> {
//                ErrorStateView(uiState.error)
            }

            uiState.data != null -> {
                LanguageSheetContent(
                    data = uiState.data,
                    onLanguageSelected = onLanguageSelected,
                    onDismiss = onDismiss
                )
            }
        }
    }
}

@Composable
private fun LanguageSheetContent(
    data: AppLanguages,
    onDismiss: () -> Unit,
    onLanguageSelected: (AppLanguages) -> Unit
) {
    val fromLanguage = data.fromLanguage
    val toLanguage = data.toLanguage
    var showFromPicker by remember { mutableStateOf(false) }
    var showToPicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        LanguageAwareText(
            modifier = Modifier.fillMaxWidth(),
            text = "Select Language", // بهتر است از stringResource استفاده شود
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,

        )

        Spacer(Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.secondaryContainer,
                    RoundedCornerShape(16.dp)
                ),
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LanguageItemView(
                    modifier = Modifier.weight(1f),
                    flagUrl = fromLanguage?.flag,
                    name = (fromLanguage?.name) ?: "",
                    onClick = { showFromPicker = true }
                )
                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = "Forward",
                )
                LanguageItemView(
                    modifier = Modifier.weight(1f),
                    flagUrl = toLanguage?.flag,
                    name = ( toLanguage?.name) ?: "",
                    onClick = { showToPicker = true }
                )
            }
        }

        Spacer(Modifier.height(24.dp))
    }


    if (showFromPicker) {
        LanguageSelectionSheet(
            title = "From Language",
            languages = data.fromLanguages,
            selectedLanguage = fromLanguage,
            onLanguageSelected = { newLanguage ->
                onLanguageSelected(data.copy(fromLanguage = newLanguage, from=newLanguage.name))
            },
            onDismiss = { showFromPicker = false }
        )
    }

    if (showToPicker) {
        LanguageSelectionSheet(
            title = "To Language",
            languages = data.toLanguages ,
            selectedLanguage = toLanguage,
            onLanguageSelected = { newLanguage ->
                onLanguageSelected(data.copy(toLanguage = newLanguage, to = newLanguage.name))
            },
            onDismiss = { showToPicker = false }
        )
    }
}

@Composable
private fun LanguageItemView(
    modifier: Modifier,
    flagUrl: String?,
    name: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = flagUrl,
                contentDescription = name,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp)),
                // اضافه کردن Placeholder برای زمانی که تصویر در حال لود است
                contentScale = ContentScale.Crop
            )
            LanguageAwareText(
                modifier = Modifier.padding(start = 8.dp),
                text = name,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}