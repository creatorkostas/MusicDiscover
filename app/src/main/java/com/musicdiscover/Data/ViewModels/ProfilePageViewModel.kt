package com.musicdiscover.Data.ViewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.musicdiscover.Data.Posts.getLikedPostsFlow
import com.musicdiscover.Data.Posts.getUserPostsFlow
import com.musicdiscover.Data.Posts.Post
import kotlinx.coroutines.flow.Flow

class ProfilePageViewModel() : ViewModel() {

    fun updatedDisplayedLikedPosts(context: Context): Flow<List<Post>> {
        val updatedDisplayedLikedPosts = getLikedPostsFlow(context)

        Log.d("updateDisplayedLikedPosts", updatedDisplayedLikedPosts.toString())
        return updatedDisplayedLikedPosts
    }

    fun updatedDisplayedUserPosts(context: Context): Flow<List<Post>> {
        val updatedDisplayedUserPosts = getUserPostsFlow(context)

        Log.d("updateDisplayedUserPosts", updatedDisplayedUserPosts.toString())
        return updatedDisplayedUserPosts
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ProfilePageViewModel()
//                val application = (this[APPLICATION_KEY] as FlightResearchApplication)
//                HomeScreenViewModel(application.database.AirportDao())
            }
        }
    }
}