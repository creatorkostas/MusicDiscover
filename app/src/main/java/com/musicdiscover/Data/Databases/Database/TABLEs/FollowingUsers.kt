package com.musicdiscover.Data.Databases.Database.TABLEs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FollowingUsers")
class FollowingUsers(
    @ColumnInfo(name = "following_user_id") val user_id: Int,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
)
