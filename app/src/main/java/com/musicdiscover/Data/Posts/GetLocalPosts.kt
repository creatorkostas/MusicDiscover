package com.musicdiscover.Data.Posts

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun getLocalPostsFlow():  Flow< MutableList<Post> > =
    flow {
        //do long work
        val sum:MutableList<Post> = getLocalPosts()
        emit(sum)
    }.flowOn(Dispatchers.Default)

fun getLocalPosts(): MutableList<Post> {
    var post_cards = mutableListOf<Post>()
//    LocalDatabase
//
//    val firebase_utils = FirebaseUtils()
//    val firebase_post = firebase_utils.getAllPosts()

//    var song_info: DocumentSnapshot?
//    var user_info: DocumentSnapshot?


//    firebase_post?.documents?.forEach {
//        song_info =
//            firebase_utils.getDocument("songs", (it.data?.get("song") as DocumentReference).id )
//        user_info =
//            firebase_utils.getDocument("users_info", (it.data?.get("user") as DocumentReference).id)
////
//        println("print s: "+song_info)
//        println("print s id: "+it.data?.get("song"))
//        println("print u: "+user_info)
//        println("print u id: "+it.data?.get("user").toString())
//        post_cards.add(
//            //Post is a DataStruct
//            Post(
//                Song(
//                    song_info?.get("name").toString(),
//                    song_info?.get("artist").toString(),
//                    song_info?.get("image").toString()
//                ),
//                User(user_info?.get("username").toString()),
//                it.data?.get("post_time") as Timestamp
//            )
//        )
//    }

    return post_cards
}