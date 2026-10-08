package am.mojtaba.armengo.ui.screen.sentence.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.model.Word
import am.mojtaba.armengo.ui.component.LanguageAwareText
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowWordSheet(
    word: Word,
    onDismiss: () -> Unit,
    onPlay: (String) -> Unit,
    onReportClick: () -> Unit = {}
) {

    val sheetState = rememberModalBottomSheetState (
        skipPartiallyExpanded = true // کانتنت کامل و بدون مرحله اضافه نشان داده شود
    )

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


            if (word.image.isNotEmpty()) {
                AsyncImage(
                    model = word.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(4.dp)
                )
            }
            Spacer(Modifier.height(32.dp))
            LanguageAwareText(
                text = word.toText ,
                fontSize = 28.sp,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Box(modifier = Modifier
                .background(
                    Color.DarkGray.copy(.2f),
                    RoundedCornerShape(25.dp)
                ),
            ) {
                LanguageAwareText(
                    text = word.phonetic,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(16.dp,0.dp)
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(16.dp))

            LanguageAwareText(
                text = word.fromText ,
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(52.dp))

            // this is for voice
            if (word.hasVoice) {
                IconButton(
                    modifier = Modifier
                        .size(64.dp, 64.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(
                            MaterialTheme.colorScheme.onTertiary,
                            RoundedCornerShape(20.dp)
                        ),
                    onClick = {
                        onPlay(word.voiceUrl)
                    }) {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = "Play"
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onReportClick) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Report"
                    )
                }
            }

            Spacer(Modifier.height(48.dp))

        }
    }
}
