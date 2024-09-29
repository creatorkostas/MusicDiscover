package com.musicdiscover.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@ExperimentalMaterial3Api
fun NavBarLayout(
    navController: NavHostController,
    addFloatingActionButton: @Composable() () -> Unit = {},
//    addFloatingActionButton: Boolean? = false,
//    floatingActionButtonPosition: FabPosition? = null,
    content: @Composable() () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (
                modifier = Modifier.width(230.dp)
//                    .height(300.dp)
//                    .fillMaxWidth(0.65f)
//                    .zIndex(1f)
            ){
                //Text("", modifier = Modifier.padding(16.dp))
                //Divider()
                NavigationDrawerItem(
                    modifier = Modifier.padding(15.dp, 15.dp, 15.dp, 15.dp),
                    label = { Text(text = "Home") },
                    selected = (getCurrentPage(navController) == Screens.Home.route),
                    onClick = {
                        navigate(navController, Screens.Home.route, null)
                        scope.launch { drawerState.apply { if (isClosed) open() else close() } }
                    }
                )
                NavigationDrawerItem(
                    modifier = Modifier.padding(15.dp, 1.dp, 15.dp, 15.dp),
                    label = { Text(text = "Settings") },
                    selected = (getCurrentPage(navController) == Screens.Settings.route),
                    onClick = {
                        navigate(navController, Screens.Settings.route, null)
                        scope.launch { drawerState.apply { if (isClosed) open() else close() } }
                    }
                )
                NavigationDrawerItem(
                    modifier = Modifier.padding(15.dp, 1.dp, 15.dp, 15.dp),
                    label = { Text(text = "About") },
                    selected = (getCurrentPage(navController) == Screens.About.route),
                    onClick = {
                        navigate(navController, Screens.About.route, null)
                        scope.launch { drawerState.apply { if (isClosed) open() else close() } }
                    }
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(15.dp, 1.dp, 15.dp, 15.dp),
                    label = { Text(text = "Dev") },
                    selected = (getCurrentPage(navController) == Screens.Dev.route),
                    onClick = {
                        navigate(navController, Screens.Dev.route, null)
                        scope.launch { drawerState.apply { if (isClosed) open() else close() } }
                    }
                )
                // ...other drawer items
            }
        },
        gesturesEnabled = true

    ) {
            Scaffold (
                topBar = { CenterAlignedTopAppBar(drawerState, scope, navController) },
                floatingActionButton = addFloatingActionButton

            ) {
                Column (
                    modifier = Modifier.padding(it)
                ) {
                    content()
                }
            }
        // Screen content
    }
}

//@Composable
//fun ScrollContent(innerPadding: PaddingValues) {}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBar(
    drawerState: DrawerState,
    scope: CoroutineScope,
    navController: NavHostController
) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "MusicDiscover",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navigate(navController, Screens.Profile.route, null)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )

}