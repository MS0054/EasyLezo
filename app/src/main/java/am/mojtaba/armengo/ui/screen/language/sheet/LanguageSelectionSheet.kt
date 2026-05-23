package am.mojtaba.armengo.ui.screen.language.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.ui.component.LanguageAwareText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectionSheet(
    title: String,
    languages: List<Language>,
    selectedLanguage: Language?,
    onLanguageSelected: (Language) -> Unit,
    onDismiss: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState (
        skipPartiallyExpanded = true
    )

    ModalBottomSheet (
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column (
            modifier = Modifier
                .padding(bottom = 16.dp)
        ) {
            // تیتر شیت
            LanguageAwareText(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )


            // لیست زبان‌ها
            LazyColumn (
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            ) {
                items(languages) { language ->
                    LanguageRowItem(
                        language = language,
                        isSelected = language.id == selectedLanguage?.id,
                        onClick = {
                            onLanguageSelected(language)
                            onDismiss() // بستن شیت بعد از انتخاب
                        }
                    )
                }
            }
        }
    }
}


@Composable
private fun LanguageRowItem(
    language: Language,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // پرچم زبان
        AsyncImage(
            model = language.flag,
            contentDescription = language.name,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(16.dp))

        // نام زبان
        LanguageAwareText(
            text = language.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        // تیک انتخاب (اگر این زبان انتخاب شده بود)
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}