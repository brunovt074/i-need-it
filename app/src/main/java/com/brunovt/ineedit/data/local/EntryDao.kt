package com.brunovt.ineedit.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {

    @Query("SELECT * FROM entries WHERE completedAt IS NULL AND `column` = :col ORDER BY updatedAt DESC, createdAt DESC")
    fun observeActive(col: String): Flow<List<EntryEntity>>

    @Query("SELECT * FROM entries WHERE completedAt IS NOT NULL ORDER BY completedAt DESC")
    fun observeDone(): Flow<List<EntryEntity>>

    @Query("SELECT * FROM entries WHERE id = :id")
    suspend fun byId(id: String): EntryEntity?

    @Upsert
    suspend fun upsert(e: EntryEntity)

    @Query("DELETE FROM entries WHERE id = :id")
    suspend fun delete(id: String)
}
