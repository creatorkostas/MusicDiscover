package com.musicdiscover.Data.Databases.Database.TABLEs

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LocalSongs")
class LocalSongs(
//    @ColumnInfo(name = "image") val image: Bytes ?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "artist") val artist: String,
    @ColumnInfo(name = "image_url") val image_url: String?,
    @ColumnInfo(name = "fb_song_id") val fb_song_id: String,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
)
