package am.mojtaba.armengo.admin.ui.screen.metadata.sheet

import am.mojtaba.armengo.core.domain.model.LastUpdate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.nio.file.WatchEvent

@Composable
fun SyncSheet(
    isExistUnSyncedUser: Boolean,
    isExistUnSyncedCategory: Boolean,
    isExistUnSyncedSentence: Boolean,
    isExistUnSyncedLanguage: Boolean,
    onConfirm: @Composable (String) -> Unit,
    onReject: @Composable (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Sync Changes",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Confirm or Reject sync server with local",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(25.dp))

        if (isExistUnSyncedUser) SyncItem("User", onConfirm = onConfirm, onReject = onReject)
        if (isExistUnSyncedCategory) SyncItem("Category", onConfirm = onConfirm, onReject = onReject)
        if (isExistUnSyncedSentence) SyncItem("Sentence", onConfirm = onConfirm, onReject = onReject)
        if (isExistUnSyncedLanguage) SyncItem("Language", onConfirm = onConfirm, onReject = onReject)


        Spacer(modifier = Modifier.height(20.dp))

    }

}

@Composable
fun SyncItem(
    collection: String,
    onConfirm: @Composable (String) -> Unit,
    onReject: @Composable (String) -> Unit
){
    var isConfirmClicked by remember { mutableStateOf( false)  }
    var isRejectClicked by remember { mutableStateOf( false)  }

    Row(
        modifier = Modifier
            .padding(32.dp, 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.fillMaxWidth().weight(1f),
                text = collection)
        IconButton( modifier = Modifier.size(48.dp), onClick = { isRejectClicked = true }) { Icon( imageVector = Icons.Rounded.Close, contentDescription = null) }
        IconButton( modifier = Modifier.size(48.dp), onClick = { isConfirmClicked = true }) { Icon( imageVector = Icons.Rounded.Check, contentDescription = null) }
    }

    if (isConfirmClicked) onConfirm(collection)
    if (isRejectClicked) onReject(collection)

}