package com.musicdiscover.Data.Posts

import android.content.Context
import com.musicdiscover.Data.Databases.Database.LocalDatabase
import com.musicdiscover.Data.Databases.Database.TABLEs.LikedPosts
import com.musicdiscover.Data.Databases.Database.TABLEs.UserPosts
import com.musicdiscover.Data.Databases.FirebaseUtils.FirebaseUtils
import com.musicdiscover.Data.Song
import com.musicdiscover.Data.User

data class Post(
    val song: Song,
    val user: User,
    val post_fb_id: String,
    val isUserPost: Boolean = false
){
    fun deletePost(context: Context){
        val db = FirebaseUtils()
        val local_db = LocalDatabase.getDatabase(context)
        db.deleteDocument("posts", db.getDocument("posts", post_fb_id)!!.id)
        local_db.likedPostsDao().delete(LikedPosts(post_fb_id))
        local_db.userPostsDao().delete(UserPosts(post_fb_id, user.fb_info_id))
    }
}

