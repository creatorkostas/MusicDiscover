package com.musicdiscover.Data.Databases.Database.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.musicdiscover.Data.Databases.Database.TABLEs.local_session

@Dao
interface SessionDao {
    @Query("SELECT * FROM Session")
    fun getAll(): MutableList<local_session>
//    fun getAll(): List<local_session>

//    @Query("SELECT * FROM local_session WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): Flow<List<local_session>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg localSession: local_session)

    @Delete
    fun delete(localSession: local_session)

    @Query("DELETE FROM Session")
    fun deleteAll()
}
