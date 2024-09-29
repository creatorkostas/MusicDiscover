package com.musicdiscover.Data.Databases.Database.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.musicdiscover.Data.Databases.Database.TABLEs.LikedPosts
import com.musicdiscover.Data.Databases.Database.TABLEs.LocalSongs
import com.musicdiscover.Data.Databases.Database.TABLEs.UserPosts

@Dao
interface LikedPostsDao {

    @Query("SELECT * FROM LikedPosts")
    fun getAll(): MutableList<LikedPosts>

//    @Query("SELECT * FROM UserPosts WHERE name LIKE :name AND artist LIKE :artist")
//    @Query("DELETE FROM LikedPosts WHERE uid IN ( SELECT uid FROM LikedPosts WHERE post_user_id = :post_user_id AND post_user_id = :post_song_id)")
//    fun findAndDelete(post_user_id: String, post_song_id: String)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert
    fun insert(liked_post: LikedPosts)

    @Delete
    fun delete(liked_post: LikedPosts)

    @Query("DELETE FROM LikedPosts")
    fun deleteAll()
}