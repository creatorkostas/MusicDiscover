package com.musicdiscover.pages.mainpage

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.musicdiscover.Data.ViewModels.MainPageViewModel
import com.musicdiscover.Data.ViewModels.ProfilePageViewModel
import com.musicdiscover.components.Posts.MakePostFloatingButton
import com.musicdiscover.components.Posts.PostCard
import com.musicdiscover.components.Posts.PostsFeed
import com.musicdiscover.navigation.NavBarLayout
import com.musicdiscover.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    navController: NavHostController,
){
    val profile_page_viewModel: ProfilePageViewModel = viewModel(factory = ProfilePageViewModel.factory)
    val main_page_viewModel: MainPageViewModel = viewModel(factory = MainPageViewModel.factory)
    val posts by main_page_viewModel.updatedDisplayedPosts().collectAsState(emptyList())
    val liked_posts by profile_page_viewModel.updatedDisplayedLikedPosts(LocalContext.current).collectAsState(emptyList())

    NavBarLayout (navController, { MakePostFloatingButton(navController, Screens.Home.route) }){
        PostsFeed {
            if (posts.isNotEmpty()) {
                posts.forEach {

                    if (liked_posts.contains(it)) PostCard(navController, it, true)
                    else PostCard(navController, it, false)
                }
            } else {
                Text(text = "There are no songs posted yet")
            }
        }
    }
}
