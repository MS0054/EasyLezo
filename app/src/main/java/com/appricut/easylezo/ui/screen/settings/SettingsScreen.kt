package com.appricut.easylezo.ui.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.appricut.easylezo.core.domain.model.Settings
import com.appricut.easylezo.ui.component.LanguageAwareText
import com.google.protobuf.LazyStringArrayList.emptyList
import kotlin.collections.emptyList

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    onBack: () -> Unit
) {

    val settingsUiState by settingsViewModel.settingsUiState.collectAsState()
    val settings = settingsUiState.data ?: Settings()

    val uriHandler = LocalUriHandler.current
    Column (
        modifier = Modifier.padding(16.dp)
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                modifier = Modifier.padding(end = 16.dp),
                onClick = {onBack()}) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null,)
            }
            LanguageAwareText(text = "Settings", modifier = Modifier.align(Alignment.Center), style = MaterialTheme.typography.headlineMedium)
        }
        Spacer(modifier = Modifier.padding(8.dp))

//        MenuItem(title = "Notification", icon = Icons.Default.Settings) {
//
//        }

        MenuItem(title = "Privacy Policy", icon = Icons.Default.Settings) {
            uriHandler.openUri(settings.policyUrl )
        }

        MenuItem(title = "Terms Of Service", icon = Icons.Default.Settings) {
            uriHandler.openUri(settings.termsUrl )
        }

//        MenuItem(title = "Logout", icon = Icons.Default.ExitToApp) {
//
//        }

//        MenuItem(title = "About", icon = Icons.Default.Info) {
//
//        }
    }



}

@Composable
fun MenuItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(  20.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        Icon(
//            imageVector = icon,
//            modifier = Modifier.padding(20.dp),
//            contentDescription = null
//        )
        LanguageAwareText(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
    }
}