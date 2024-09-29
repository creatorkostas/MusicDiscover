package com.musicdiscover.Data.Databases.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.musicdiscover.Data.Databases.Database.DAOs.LikedPostsDao
import com.musicdiscover.Data.Databases.Database.DAOs.LocalPostsDao
import com.musicdiscover.Data.Databases.Database.DAOs.LocalSongsDao
import com.musicdiscover.Data.Databases.Database.DAOs.SessionDao
import com.musicdiscover.Data.Databases.Database.TABLEs.FollowingUsers
import com.musicdiscover.Data.Databases.Database.TABLEs.LikedPosts
import com.musicdiscover.Data.Databases.Database.TABLEs.LocalSongs
import com.musicdiscover.Data.Databases.Database.TABLEs.UserPosts
import com.musicdiscover.Data.Databases.Database.TABLEs.local_session

@Database(entities = [
    local_session::class,
    FollowingUsers::class,
    LikedPosts::class,
    LocalSongs::class,
    UserPosts::class
    ],
    version = 8,
    exportSchema = false)
abstract class LocalDatabase : RoomDatabase()  {
    abstract fun sessionDao(): SessionDao

    abstract fun localSongsDao(): LocalSongsDao

    abstract fun userPostsDao(): LocalPostsDao
    abstract fun likedPostsDao(): LikedPostsDao

    companion object {
        @Volatile
        private var Instance: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    LocalDatabase::class.java,
                    "local-db"
                ).allowMainThreadQueries() // To allow queries on the main thread
                    .fallbackToDestructiveMigration() // Clear the database if no migration is defined
                    .build().also { Instance = it }

            }
        }
    }

}