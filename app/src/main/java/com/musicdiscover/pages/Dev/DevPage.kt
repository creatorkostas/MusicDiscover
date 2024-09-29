package com.musicdiscover.pages.Dev

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.musicdiscover.Data.Databases.DataStoreUtils.DataStoreUtil
import com.musicdiscover.Data.Databases.DataStoreUtils.UserFields
import com.musicdiscover.Data.Databases.Database.LocalDatabase
import com.musicdiscover.Data.Databases.FirebaseUtils.FirebaseUtils
import com.musicdiscover.Data.Posts.getLikedPosts
import com.musicdiscover.Data.Posts.getUserPosts
import com.musicdiscover.Data.devPrint
import com.musicdiscover.navigation.NavBarLayout
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevPage(navController: NavHostController, context: Context){

//            val profile_page_viewModel: ProfilePageViewModel = viewModel(factory = ProfilePageViewModel.factory)
//            val liked_posts by profile_page_viewModel.updatedDisplayedLikedPosts(context).collectAsState(emptyList())
    NavBarLayout(navController = navController) {
        Button(onClick = { LocalDatabase.getDatabase(context).localSongsDao().deleteAll() }) { Text(text = "Delete local songs") }
        Button(onClick = { LocalDatabase.getDatabase(context).likedPostsDao().deleteAll() }) { Text(text = "Delete liked posts") }
        Button(onClick = { LocalDatabase.getDatabase(context).userPostsDao().deleteAll() }) { Text(text = "Delete user posts") }
        Button(onClick = { runBlocking { DataStoreUtil(context).saveIn_User_DataStore<String>(
            UserFields.USER_AUTH_TOKEN, "") } }) { Text(text = "Delete local token") }
        Button(onClick = { LocalDatabase.getDatabase(context).sessionDao().deleteAll() }) { Text(text = "Delete local session") }
        Button(onClick = { FirebaseUtils().db.disableNetwork() }) { Text(text = "Disable firebase network") }
        Button(onClick = { FirebaseUtils().db.enableNetwork() }) { Text(text = "Enable  firebase network") }
        Button(onClick = {
            LocalDatabase.getDatabase(context).likedPostsDao().getAll().forEach {
                devPrint(it.post_id)
            }
//            print("-----------------------------")
//            liked_posts.forEach {
//                print(it.toString())
//            }
        }) { Text(text = "Log liked posts ROOM") }
        Button(onClick = {
            getLikedPosts(context).forEach {
                devPrint(it.toString())
            }

        }) { Text(text = "Log user posts") }
        Button(onClick = {
            getUserPosts(context).forEach {
                devPrint(it.toString())
            }

        }) { Text(text = "Log user posts") }
    }
}

