package com.musicdiscover.components.layouts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.musicdiscover.navigation.NavBarLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBarLayoutOLD(
    navController: NavHostController,
//    floatingActionButton: Boolean?,
    addFloatingActionButton: @Composable() () -> Unit = {},
    content: @Composable() () -> Unit
){

    NavBarLayout(navController, addFloatingActionButton){ content() }
}