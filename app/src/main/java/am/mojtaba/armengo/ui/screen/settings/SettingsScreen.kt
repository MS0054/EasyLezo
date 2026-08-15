package am.mojtaba.armengo.ui.screen.settings

import am.mojtaba.armengo.app.R
import am.mojtaba.armengo.core.data.datastore.enums.ThemeMode
import am.mojtaba.armengo.ui.component.LanguageAwareText
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onBackClick: () -> Unit,
    onThemeToggle: (Boolean) -> Unit,
    onPolicyClick: () -> Unit,
    onTermsClick: () -> Unit,
    onLoginClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 42.dp, start = 16.dp, end = 16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }

//        Spacer(modifier = Modifier.height(8.dp))

        UserSection(
            uiState = uiState,
            onLoginClick = onLoginClick
        )

        Spacer(modifier = Modifier.height(48.dp))

        val isDark = when (uiState.themeMode) {
            ThemeMode.DARK -> true
            ThemeMode.LIGHT -> false
            ThemeMode.SYSTEM -> isSystemInDarkTheme()
        }

        ThemeToggleItem(
            title = "Dark Mode",
            isDark = isDark,
            onToggle = onThemeToggle
        )

        MenuItem(
            title = "Privacy Policy",
            icon = Icons.Default.Settings,
            onClick = onPolicyClick
        )

        MenuItem(
            title = "Terms Of Service",
            icon = Icons.Default.Settings,
            onClick = onTermsClick
        )

        if (uiState.user != null ){ SignOutItem { onSignOutClick() } }
    }
}

@Composable
fun ThemeToggleItem(
    title: String,
    isDark: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LanguageAwareText(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Switch(
            checked = isDark,
            onCheckedChange = onToggle
        )
    }
}
@Composable
fun MenuItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LanguageAwareText(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun SignOutItem(
    onSignOutClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSignOutClick)
            .padding(20.dp, 25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LanguageAwareText(
            text = "Logout",
            color = Color.Red,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun UserSection(
    uiState: SettingsUiState,
    onLoginClick: () -> Unit
) {
    val user = uiState.user
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            if (user?.photoUrl.isNullOrEmpty()) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                AsyncImage(
                    model = user.photoUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (user != null) {
            Text(
                text = user.displayName,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                LanguageAwareText(text = "Sign In")
            }
        }
    }
}