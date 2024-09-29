package com.musicdiscover.Data.Databases.Database.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.musicdiscover.Data.Databases.Database.TABLEs.LocalSongs
import com.musicdiscover.Data.Databases.Database.TABLEs.UserPosts

@Dao
interface LocalPostsDao {

    @Query("SELECT * FROM UserPosts")
    fun getAll(): MutableList<UserPosts>

//    @Query("SELECT * FROM UserPosts WHERE name LIKE :name AND artist LIKE :artist")
//    fun getByFields(name: String, artist: String): MutableList<UserPosts>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg user_post: UserPosts)

    @Delete
    fun delete(user_post: UserPosts)

    @Query("DELETE FROM UserPosts")
    fun deleteAll()
}