package com.appricut.easylezo.ui.screen.settings

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.appricut.easylezo.ui.component.LanguageAwareText

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    onBack: () -> Unit
) {

    Column (
        modifier = Modifier.padding(16.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.padding(end = 16.dp),
                onClick = {onBack()}) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null,)
            }
            LanguageAwareText(text = "Settings", style = MaterialTheme.typography.headlineMedium)
        }
        Spacer(modifier = Modifier.padding(8.dp))

        MenuItem(title = "Notification", icon = Icons.Default.Settings) {

        }

        MenuItem(title = "Logout", icon = Icons.Default.ExitToApp) {

        }

        MenuItem(title = "About", icon = Icons.Default.Info) {

        }
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
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            modifier = Modifier.padding(20.dp),
            contentDescription = null
        )
        LanguageAwareText(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
    }
}