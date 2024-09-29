package com.musicdiscover.Data.Posts

import android.content.Context
import com.google.firebase.firestore.DocumentReference
import com.musicdiscover.Data.APIs.ApiEndpoints
import com.musicdiscover.Data.APIs.GeniusApiEndpoints
import com.musicdiscover.Data.APIs.GeniusRetrofitHelpers
import com.musicdiscover.Data.APIs.LastFmRetrofitHelper
import com.musicdiscover.Data.Databases.DataStoreUtils.DataStoreUtil
import com.musicdiscover.Data.Databases.DataStoreUtils.UserFields
import com.musicdiscover.Data.Databases.Database.LocalDatabase
import com.musicdiscover.Data.Databases.Database.TABLEs.LocalSongs
import com.musicdiscover.Data.Databases.Database.TABLEs.UserPosts
import com.musicdiscover.Data.Databases.FirebaseUtils.FirebaseUtils
import com.musicdiscover.Data.Song
import com.musicdiscover.Data.devPrint
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class MakePost(
    song_name: String,
    context: Context,
//    user: UserViewModel
) {

    val user_dataStore = DataStoreUtil(context)
    var song_name = song_name
    var context = context
    var user_id: String? = null
    var song_id: DocumentReference? = null

    private var song: Song? = null
    private var local_success = false
    private var firebase_success:DocumentReference? = null
    fun post(): Boolean {
        getSong()
        getUserId()
        song?.image?.let { devPrint(it) }

        if ( song != null) {
            firebase_success = postSongFirebase()
            if (firebase_success != null) local_success = postSongLocal()

            if (!(local_success && firebase_success != null)) {
                if (local_success) {deletePostSongLocal()}
                if (firebase_success  != null) {deletePostSongFirebase()}
            }else{
                LocalDatabase.getDatabase(context).userPostsDao().insert(UserPosts(firebase_success!!.id, user_id!!))
            }
        }

        return local_success && firebase_success != null
    }

    private suspend fun validateSong(): Song? {

        var songname: String? = null
        var songartist: String? = null
        var songimage: String? = null
        try {
            val genius_Api = GeniusRetrofitHelpers.getInstance().create(GeniusApiEndpoints::class.java)
            val result = genius_Api.getSong("search?q=$song_name&access_token=jTJRCLJZn1svbzXHMoKAShU9Kj59j_fJaMBH_YzcmYf0Gzo0HglqAAXnoLtCqwFr")
            songname = result.body()?.response?.hits?.get(0)?.result?.title
            songartist = result.body()?.response?.hits?.get(0)?.result?.artist_names
            songimage = result.body()?.response?.hits?.get(0)?.result?.song_art_image_url
        }catch (ex: Exception){
            val lastfm_Api = LastFmRetrofitHelper.getInstance().create(ApiEndpoints::class.java)
            val result = lastfm_Api.getSong("?method=track.search&track=$song_name&api_key=979058be5971b3f2282a21f6cc28f148&format=json")
            songname = result.body()?.results?.trackmatches?.track?.get(0)?.name
            songartist = result.body()?.results?.trackmatches?.track?.get(0)?.artist
        }

        return if (songname != null && songartist != null) Song(name = songname, artist = songartist, image = songimage)
        else null

    }
    private fun postSongLocal(): Boolean {
        val db = LocalDatabase.getDatabase(context).localSongsDao()

        if(song != null && db.getByFields(song!!.name,song!!.artist).isEmpty() ) {
            db.insert(LocalSongs(song!!.name,song!!.artist, song!!.image, song_id!!.id))
            return true
        }
        return song != null
    }
    private fun postSongFirebase(): DocumentReference? {
        val firebase = FirebaseUtils()
        if(song != null ){
            song_id = firebase.addSong(song!!)
            val post_ref = firebase.addPost(context, song_id!!)
            if(post_ref != null) return post_ref
        }
        return null
    }

    private fun deletePostSongLocal(): Boolean {
        val db = LocalDatabase.getDatabase(context)
        if(song != null) {
            db.localSongsDao().delete(LocalSongs(song!!.name,song!!.artist, song!!.image, song_id!!.id))
            return true
        }
        return false
    }
    private fun deletePostSongFirebase() {}

    private fun getSong(){
        val deferredResult: Deferred<Song?> = GlobalScope.async {
            validateSong()
        }
        runBlocking {
            val result = deferredResult.await()
            song = result

        }
    }
    private fun getUserId(){
//        val deferredResult: Deferred<String?> = GlobalScope.async {
//
//        }
        runBlocking {
            user_id = user_dataStore.readFrom_User_DataStore<String>(UserFields.USER_ID)
//            deferredResult.await()
        }

//        println("print: uid "+user_id)
    }

}