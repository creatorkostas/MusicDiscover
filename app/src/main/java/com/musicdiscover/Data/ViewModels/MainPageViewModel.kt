package com.musicdiscover.Data.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.musicdiscover.Data.Posts.getPostsFlow
import com.musicdiscover.Data.Posts.Post
import kotlinx.coroutines.flow.Flow

class MainPageViewModel() : ViewModel() {

    fun updatedDisplayedPosts(): Flow<List<Post>> {
        val updatedDisplayedPosts = getPostsFlow()

        Log.d("updateDisplayedPosts", updatedDisplayedPosts.toString())
        return updatedDisplayedPosts
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainPageViewModel()
//                val application = (this[APPLICATION_KEY] as FlightResearchApplication)
//                HomeScreenViewModel(application.database.AirportDao())
            }
        }
    }
}