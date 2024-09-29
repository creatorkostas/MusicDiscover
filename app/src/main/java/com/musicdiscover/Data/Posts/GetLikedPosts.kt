package com.musicdiscover.Data.Posts

import android.content.Context
import com.musicdiscover.Data.Databases.DataStoreUtils.DataStoreUtil
import com.musicdiscover.Data.Databases.DataStoreUtils.UserFields
import com.musicdiscover.Data.Databases.Database.LocalDatabase
import com.musicdiscover.Data.Databases.FirebaseUtils.FirebaseUtils
import com.musicdiscover.Data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

fun getLikedPostsFlow(context: Context):  Flow< MutableList<Post> > =
    flow {
        //do long work
        val sum2:MutableList<Post> = getLikedPosts(context)
        emit(sum2)
    }.flowOn(Dispatchers.IO)

fun getUserPostsFlow(context: Context):  Flow< MutableList<Post> > =
    flow {
        //do long work
        var sum:MutableList<Post> = getUserPosts(context)
        
        emit(sum)
    }.flowOn(Dispatchers.IO)

fun getUserPosts(context: Context): MutableList<Post> {
    var user_posts = mutableListOf<Post>()
    val user: User
    runBlocking {
        user = User(
            name = DataStoreUtil(context).readFrom_User_DataStore<String>(UserFields.USER_NAME).toString(),
            fb_info_id =  DataStoreUtil(context).readFrom_User_DataStore<String>(UserFields.USER_ID).toString()
        )

        val local_posts = LocalDatabase.getDatabase(context).userPostsDao().getAll()
        local_posts.forEach {
            if (it.post_user_id == user.fb_info_id) {
                user_posts.add(
                    generatePost(
                        FirebaseUtils(),
                        FirebaseUtils().getDocument("posts", it.post_id)!!,
                        true
                    )!!

                )
            }
        }
    }
    return user_posts
}

fun getLikedPosts(context: Context): MutableList<Post> {
    var liked_posts = mutableListOf<Post>()

    runBlocking {
        val local_liked_songs = LocalDatabase.getDatabase(context).likedPostsDao().getAll()
        val db = FirebaseUtils()
        local_liked_songs.forEach { it ->

            liked_posts.add(generatePost(db, db.getDocument("posts", it.post_id)!!)!!)

        }
    }
//    println("print: "+liked_posts.toString())

    return liked_posts
}