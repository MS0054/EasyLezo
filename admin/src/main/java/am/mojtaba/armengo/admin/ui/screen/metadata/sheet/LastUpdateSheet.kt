package am.mojtaba.armengo.admin.ui.screen.metadata.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import am.mojtaba.armengo.core.domain.model.LastUpdate


@Composable
fun LastUpdateSheet(
    onNewLastUpdate: (LastUpdate) -> Unit
) {
    var existUser by remember { mutableStateOf(false) }
    var existCategory by remember { mutableStateOf(false) }
    var existSentence by remember { mutableStateOf(false) }
    var existLanguage by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text("Select data to refresh")

            Spacer(modifier = Modifier.height(12.dp))

            CheckboxItem("User", existUser) { existUser = it }
            CheckboxItem("Category", existCategory) { existCategory = it }
            CheckboxItem("Sentence", existSentence) { existSentence = it }
            CheckboxItem("Language", existLanguage) { existLanguage = it }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val result = LastUpdate(
                        existNewUserData = existUser,
                        existNewCategoryData = existCategory,
                        existNewSentenceData = existSentence,
                        existNewLanguageData = existLanguage
                    )

                    onNewLastUpdate(result)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Refresh")
            }
        }

}

@Composable
fun CheckboxItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(text = title)
    }
}