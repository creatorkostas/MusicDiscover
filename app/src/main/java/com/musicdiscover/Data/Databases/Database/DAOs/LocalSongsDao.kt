package com.musicdiscover.Data.Databases.Database.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.musicdiscover.Data.Databases.Database.TABLEs.LocalSongs
import com.musicdiscover.Data.Databases.Database.TABLEs.local_session

@Dao
interface LocalSongsDao {
    @Query("SELECT * FROM LocalSongs")
    fun getAll(): MutableList<LocalSongs>

    @Query("SELECT * FROM LocalSongs WHERE name LIKE :name AND artist LIKE :artist")
    fun getByFields(name: String, artist: String): MutableList<LocalSongs>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg localSong: LocalSongs)

    @Delete
    fun delete(localSong: LocalSongs)

    @Query("DELETE FROM LocalSongs")
    fun deleteAll()
}
