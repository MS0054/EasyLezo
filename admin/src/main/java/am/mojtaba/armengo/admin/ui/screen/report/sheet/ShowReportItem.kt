package am.mojtaba.armengo.admin.ui.screen.report.sheet

import am.mojtaba.armengo.admin.ui.component.LanguageAwareText
import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.model.Word
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowReportItem(
    sentence: Sentence? = null,
    word: Word? = null,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val image = word?.image ?: sentence?.image ?: ""
    val toText = word?.toText ?: sentence?.toText ?: ""
    val phonetic = word?.phonetic ?: sentence?.phonetic ?: ""
    val fromText = word?.fromText ?: sentence?.fromText ?: ""

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (image.isNotEmpty()) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(4.dp)
                )
                Spacer(Modifier.height(16.dp))
            }

            LanguageAwareText(
                text = toText,
                fontSize = 28.sp,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            if (phonetic.isNotBlank()) {
                Box(
                    modifier = Modifier.background(
                        Color.DarkGray.copy(.2f),
                        RoundedCornerShape(25.dp)
                    )
                ) {
                    LanguageAwareText(
                        text = phonetic,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .padding(16.dp, 0.dp)
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(Modifier.height(16.dp))
            }

            LanguageAwareText(
                text = fromText,
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}
