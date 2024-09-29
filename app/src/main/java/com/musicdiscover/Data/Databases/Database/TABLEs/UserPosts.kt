package com.musicdiscover.Data.Databases.Database.TABLEs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

@Entity(tableName = "UserPosts")
class UserPosts(
    @PrimaryKey
    @ColumnInfo(name = "post_id") val post_id: String,
    @ColumnInfo(name = "post_user_id") val post_user_id: String
//    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
)
