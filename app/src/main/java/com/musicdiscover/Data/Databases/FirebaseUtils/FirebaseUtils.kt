package com.musicdiscover.Data.Databases.FirebaseUtils

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.musicdiscover.Data.Databases.DataStoreUtils.DataStoreUtil
import com.musicdiscover.Data.Databases.DataStoreUtils.UserFields
import com.musicdiscover.Data.Posts.generatePost
import com.musicdiscover.Data.Posts.Post
import com.musicdiscover.Data.Song
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.asDeferred

class FirebaseUtils {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun addSong(song: Song): DocumentReference {
        val newSongRef: DocumentReference
        val song_in_db = isSongInFirebase(song)
        newSongRef = if (song_in_db != null) song_in_db.reference
        else{
            val song_data = hashMapOf(
                "name" to "${song.name}",
                "artist" to "${song.artist}",
                "image" to "${song.image}"
            )
            postDataInFirebase<String>("songs" , song_data)

        }

        return newSongRef
    }

    fun addPost(context: Context, song_ref: DocumentReference): DocumentReference? {
        val time = Timestamp.now()
        var user_ref: DocumentReference? = null

        runBlocking {
            val result = DataStoreUtil(context).readFrom_User_DataStore<String>(UserFields.USER_ID)
            if (result != null) {
                user_ref = FirebaseUtils().getDocument("users_info", result)?.reference
            }
        }

        if(user_ref != null){
            val post_data = hashMapOf(
                "song" to song_ref,
                "user" to user_ref,
                "post_time" to time
            )
            val newPostRef = postDataInFirebase<Any?>("posts" , post_data)

            return newPostRef
        }

        return  null
    }

    fun getUserInfoDocument(user_id: DocumentReference): DocumentSnapshot? {
        var result = runFirebaseTaskSync<QuerySnapshot>(db.collection("users_info").whereEqualTo("user", user_id).get())
        var user_info: DocumentSnapshot? = null
        if (result != null && result.documents.isNotEmpty()) {
            user_info = result.documents[0]
        }
        return user_info
    }

    fun getAllPosts(collection: String? = null): QuerySnapshot? {
        var posts: QuerySnapshot? = runFirebaseTaskSync<QuerySnapshot>(db.collection(collection ?: "posts").get())
        val source = if (posts!!.metadata.isFromCache) {
            "local cache"
        } else {
            "server"
        }
        println("Data fetched from $source")
        println("Data fetched from "+posts!!.metadata.toString())

        if (posts != null && posts.isEmpty) posts = null
        return posts
    }

    fun addSession(token: String, user_id: DocumentReference){
        val newSessionRef = db.collection("sessions").document()
        val session = hashMapOf(
            "Token" to "${token}",
            "last_login" to Timestamp.now(),
            "user" to user_id
        )

        newSessionRef.set(session).addOnSuccessListener{}.addOnFailureListener { e -> println("passed "+ e.message)}
    }

    fun getDocument(collection: String, doc_id: String): DocumentSnapshot? {
        return runFirebaseTaskSync(db.collection(collection).document(doc_id).get())
    }

    fun isSongInFirebase(song: Song): DocumentSnapshot? {
        var result = runFirebaseTaskSync<QuerySnapshot>( db.collection("songs")
            .whereEqualTo("artist", song.artist)
            .whereEqualTo("name", song.name)
            .get())
        var exist: DocumentSnapshot? = null
        if (result != null && result.documents.isNotEmpty()) {
            exist = result.documents[0]
        }
        return exist
    }

    fun getUser(email: String, password: String): DocumentSnapshot?{
        var doc: QuerySnapshot? = runFirebaseTaskSync<QuerySnapshot>(db.collection("users").whereEqualTo("email", email).whereEqualTo("password", password).get())
        return if ((doc != null) && !(doc.isEmpty)) doc.documents.get(0)
        else null
    }

    fun deleteDocument(collection: String, doc_id: String){
        runFirebaseTaskSync(db.collection(collection).document(doc_id).delete(), false)
    }

    fun <T> searchAndGetDocument(collection: String, field: String, value: T): DocumentSnapshot? {
        val doc: QuerySnapshot? = runFirebaseTaskSync<QuerySnapshot?>(db.collection(collection).whereEqualTo(field, value).get())
        return if (doc == null || doc.documents.isEmpty()) null
        else doc.documents[0]
    }

    fun <T> runFirebaseTaskSync(firebase_task: Task<T>, run_in_sync: Boolean = true): T? {
        var result: T?
        val task = firebase_task.addOnSuccessListener { document ->
            return@addOnSuccessListener
        }.addOnFailureListener { exception -> }.asDeferred()

        if (run_in_sync) {
            runBlocking {
                result = task.await() as T
            }
        }else{
            result = null
        }

        return if (result != null) result
        else null
    }

    private fun <T> postDataInFirebase(collection: String, data: HashMap<String, T>): DocumentReference{
        val newRef = db.collection(collection).document()

        val firebase_task = newRef
            .set(data)
            .addOnSuccessListener{ /*TODO*/ }
            .addOnFailureListener { /*TODO*/ }
        runFirebaseTaskSync(firebase_task, false)

        return newRef
    }

}