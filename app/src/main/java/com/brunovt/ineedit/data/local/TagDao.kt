package com.brunovt.ineedit.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Query("SELECT * FROM tags ORDER BY sortOrder ASC, name ASC")
    fun observeAll(): Flow<List<TagEntity>>

    @Query("SELECT * FROM tags ORDER BY sortOrder ASC, name ASC")
    suspend fun all(): List<TagEntity>

    @Query("SELECT * FROM tags WHERE id = :id")
    suspend fun byId(id: String): TagEntity?

    @Upsert
    suspend fun upsert(tag: TagEntity)

    @Query("DELETE FROM tags WHERE id = :id")
    suspend fun delete(id: String)
}
