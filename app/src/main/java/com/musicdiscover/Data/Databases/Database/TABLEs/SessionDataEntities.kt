package com.musicdiscover.Data.Databases.Database.TABLEs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Session")
class local_session(
//    @ColumnInfo(name = "Token") val token: String,
    @PrimaryKey
    @ColumnInfo(name = "token") val token: String
)
