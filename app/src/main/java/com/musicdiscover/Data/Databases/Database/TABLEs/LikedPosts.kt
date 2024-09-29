package com.musicdiscover.Data.Databases.Database.TABLEs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LikedPosts", primaryKeys = ["post_id"])
class LikedPosts(
    @ColumnInfo(name = "post_id") val post_id: String, //user id in firebase
//    @ColumnInfo(name = "post_song_id") val post_song_id: String //song in local songs
)
