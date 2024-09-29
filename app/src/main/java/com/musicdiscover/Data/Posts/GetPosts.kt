package com.musicdiscover.Data.Posts

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.musicdiscover.Data.Databases.FirebaseUtils.FirebaseUtils
import com.musicdiscover.Data.Song
import com.musicdiscover.Data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun getPostsFlow():  Flow< MutableList<Post> > =
    flow {
        //do long work
        val sum:MutableList<Post> = getPosts()
        emit(sum)
    }.flowOn(Dispatchers.IO)

fun getPosts(collection: String? = null): MutableList<Post> {
    val post_cards = mutableListOf<Post>()
//    CoroutineScope(Dispatchers.IO).launch{
        val firebase_utils = FirebaseUtils()
        val firebase_post = firebase_utils.getAllPosts(collection)


        firebase_post?.documents?.forEach {
            post_cards.add(generatePost(firebase_utils, it)!!)
        }

    return post_cards
}

fun generatePost(firebase_utils: FirebaseUtils, post_document: DocumentSnapshot, isUsers: Boolean = false): Post? {
    var post: Post? = null

    val song_info =
        firebase_utils.getDocument("songs", (post_document.data?.get("song") as DocumentReference).id )
    val user_info =
        firebase_utils.getDocument("users_info", (post_document.data?.get("user") as DocumentReference).id)
//
//        println("print s: "+song_info)
//        println("print s id: "+it.data?.get("song"))
//        println("print u: "+user_info)
//        println("print u id: "+it.data?.get("user").toString())
    if (user_info != null && song_info != null){
//                post_cards.add(
        //Post is a DataStruct
        post = Post(
            Song(
                song_info.get("name").toString(),
                song_info.get("artist").toString(),
                fb_song_id = song_info.id,
                song_info.get("image").toString(),
            ),
            User(user_info.get("username").toString(), fb_info_id =  user_info.id),
            post_document.id,
            isUsers
//                        it.data?.get("post_time") as Timestamp
        )
//                )
    }

    return post
}