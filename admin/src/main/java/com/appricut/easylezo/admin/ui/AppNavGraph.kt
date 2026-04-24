package com.appricut.easylezo.admin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.appricut.easylezo.admin.ui.screen.category.CategoryV
import com.appricut.easylezo.admin.ui.screen.auth.AuthScreen
import com.appricut.easylezo.admin.ui.screen.auth.AuthV
import com.appricut.easylezo.admin.ui.screen.category.CategoryS
import com.appricut.easylezo.admin.ui.screen.language.LanguageS
import com.appricut.easylezo.admin.ui.screen.language.LanguageV
import com.appricut.easylezo.admin.ui.screen.metadata.MetadataV
import com.appricut.easylezo.admin.ui.screen.resource.ResourceS
import com.appricut.easylezo.admin.ui.screen.resource.ResourceV
import com.appricut.easylezo.admin.ui.screen.sentence.SentenceS
import com.appricut.easylezo.admin.ui.screen.sentence.SentenceV
import com.appricut.easylezo.admin.ui.screen.splash.SplashScreen
import com.appricut.easylezo.admin.ui.screen.splash.SplashV
import com.appricut.easylezo.admin.ui.screen.user.UserS
import com.appricut.easylezo.admin.ui.screen.user.UserV
import com.appricut.easylezo.admin.ui.sheet.AppSheet
import com.appricut.easylezo.admin.ui.sheet.SheetManager
import com.appricut.easylezo.admin.ui.sheet.SheetV

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Auth : Screen("auth")
    data object Category : Screen("category")
    data object Language : Screen("language")
    data object Resource : Screen("resource")
    data object User : Screen("user")
    object Sentence : Screen("sentence/{categoryId}") {
        fun createRoute(
            categoryId: String
        ) = "sentence/$categoryId"
    }
}

enum class RefreshData {
    PROGRESS, DONE, ERROR, SYNC
}

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var refreshStatus by remember { mutableStateOf(RefreshData.DONE) }
    var isSynced by remember { mutableStateOf(false) }
    var refreshIconColor by remember { mutableStateOf(Color.Gray) }
    val currentRoute = navBackStackEntry?.destination?.route
    val splashV: SplashV = hiltViewModel()
    val authV: AuthV = hiltViewModel()
    val categoryV: CategoryV = hiltViewModel()
    val sentenceV: SentenceV = hiltViewModel()
    val languageV: LanguageV = hiltViewModel()
    val metadataV: MetadataV = hiltViewModel()
    val resourceV: ResourceV = hiltViewModel()
    val userV: UserV = hiltViewModel()
    val sheetV: SheetV = hiltViewModel()


    SheetManager(
        sheetV,
        authV,
        userV,
        categoryV,
        sentenceV,
        languageV,
        metadataV,
        resourceV,
        onLogoutSuccess = {
            navController.navigate(Screen.Splash.route) {
                popUpTo(Screen.Auth.route) {
                    inclusive = true
                }
            }
        },
        onRefresh = { refreshStatus = it },
        isSynced = { isSynced = it }
    )
    refreshIconColor = when (refreshStatus) {
        RefreshData.PROGRESS -> Color.Yellow
        RefreshData.DONE -> Color.Gray
        RefreshData.ERROR -> Color.Red
        RefreshData.SYNC -> Color.Cyan
    }
    Scaffold(
        topBar = {
            DynamicHeader(
                refreshIconColor = refreshIconColor,
                currentRoute = currentRoute,
                onScreenOpen = { navController.navigate(it.route) },
                onSheetOpen = { sheetV.openSheet(it) },
                isSynced = isSynced,
                onRefresh = { splashV.start(true)}
            )
        }
    ) { paddingValues ->
        MyNavHost(
            navController,
            Modifier.padding(paddingValues),
            splashV,
            categoryV,
            sentenceV,
            authV,
            userV,
            metadataV,
            languageV,
            resourceV,
            sheetV
        )
    }
}


@Composable
fun MyNavHost(
    navController: NavHostController,
    modifier: Modifier,
    splashV: SplashV,
    categoryV: CategoryV,
    sentenceV: SentenceV,
    authV: AuthV,
    userV: UserV,
    metadataV: MetadataV,
    languageV: LanguageV,
    resourceV: ResourceV,
    sheetV: SheetV
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(splashV) { route ->
                navController.navigate(route) { popUpTo(Screen.Splash.route) { inclusive = true } }
            }
        }
        composable(Screen.Auth.route) {
            AuthScreen(authV) {
                navController.navigate(Screen.Splash.route) {
                    popUpTo(Screen.Auth.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(Screen.Category.route) {
            CategoryS(
                categoryV,
                onEdit = { sheetV.openSheet(AppSheet.EditCategory(it)) },
                onAdd = { sheetV.openSheet(AppSheet.AddCategory(it)) },
                openSentences = { navController.navigate(Screen.Sentence.createRoute(it)) },
            )
        }
        composable(Screen.Language.route) {
            LanguageS (
                languageV,
                onEdit = { sheetV.openSheet(AppSheet.EditLanguage(it)) },
                onAdd = { sheetV.openSheet(AppSheet.AddLanguage(it)) }
            )
        }
        composable(Screen.User.route) {
            UserS (
                userV = userV,
                onEdit = { sheetV.openSheet(AppSheet.EditUser(it)) },
            )
        }
        composable(Screen.Resource.route) {
            ResourceS (
                resourceV = resourceV ,
                onEdit = { sheetV.openSheet(AppSheet.EditResource(it)) },
                onAdd = { sheetV.openSheet(AppSheet.AddCategory(it)) },
                openSentences = { }
            )
        }

        composable(Screen.Sentence.route) { backStackEntry->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            SentenceS(
                categoryId,
                sentenceV,
                onAdd = { sheetV.openSheet(AppSheet.AddSentence(it)) },
                onEdit = { sheetV.openSheet(AppSheet.EditSentence(it))
            })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicHeader(
    refreshIconColor: Color,
    currentRoute: String?,
    onScreenOpen: (Screen) -> Unit,
    onSheetOpen: (AppSheet) -> Unit,
    isSynced: Boolean,
    onRefresh: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    var onSyncClicked: () -> Unit = {}
    TopAppBar(
        title = {
            if ((currentRoute != Screen.Auth.route) && (currentRoute != Screen.Splash.route)) {
            Row(
                modifier = Modifier.clickable { showMenu = !showMenu },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentRoute?.split("/")[0] ?: "",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineMedium,
                )
                Icon(Icons.Rounded.KeyboardArrowDown, contentDescription = "")
            }
                }
            //------------ DROPDOWN MENU -------------
            DropdownMenu(
                expanded = showMenu,
                shape = RoundedCornerShape(16.dp),
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Refresh") },
                    onClick = { showMenu = false; onRefresh() })
                DropdownMenuItem(
                    text = { Text("Category") },
                    onClick = { showMenu = false; onScreenOpen(Screen.Category) })
                DropdownMenuItem(
                    text = { Text("User") },
                    onClick = { showMenu = false; onScreenOpen(Screen.User) })
                DropdownMenuItem(
                    text = { Text("Language") },
                    onClick = { showMenu = false; onScreenOpen(Screen.Language) })
                DropdownMenuItem(
                    text = { Text("Resource") },
                    onClick = { showMenu = false; onScreenOpen(Screen.Resource) })
                DropdownMenuItem(
                    text = { Text("Logout", color = Color.Red) },
                    onClick = { showMenu = false; onSheetOpen(AppSheet.LogoutConfirm) })
                DropdownMenuItem(
                    text = { Text("AppLanguages", color = Color.Gray) },
                    onClick = { showMenu = false; onSheetOpen(AppSheet.AppLanguage) })
                DropdownMenuItem(
                    text = { Text("Settings", color = Color.Gray) },
                    onClick = { showMenu = false; onSheetOpen(AppSheet.Settings) })
                DropdownMenuItem(
                    text = { Text("UpdateDatabase", color = Color.Gray) },
                    onClick = { showMenu = false; onSheetOpen(AppSheet.LastUpdate) })
            }
        },
        actions = {
            if (currentRoute == Screen.Category.route) {
                if (isSynced) onSyncClicked = { onSheetOpen(AppSheet.Sync) }
                IconButton(onClick = { onSheetOpen(AppSheet.SortCategory) }) {
                    Icon(Icons.Default.List, contentDescription = null)
                }
            }
            if (currentRoute == Screen.Sentence.route) {
                IconButton(onClick = { onSheetOpen(AppSheet.SortSentence) }) {
                    Icon(Icons.Default.List, contentDescription = null)
                }
            }
            if (currentRoute == Screen.Language.route) {
                IconButton(onClick = { onSheetOpen(AppSheet.SortLanguage) }) {
                    Icon(Icons.Default.List, contentDescription = null)
                }
            }
            if (currentRoute == Screen.Resource.route) {
                IconButton(onClick = { onSheetOpen(AppSheet.AddResource) }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
            Box(  modifier = Modifier.padding(end = 16.dp).size(16.dp).background(color = refreshIconColor.copy(.4f), shape = CircleShape).clickable(
                onClick = { onSyncClicked() }
            ))



        },
        )
}
