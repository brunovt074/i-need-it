package com.brunovt.ineedit.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface StatusDao {

    @Query("SELECT * FROM statuses ORDER BY sortOrder ASC, name ASC")
    fun observeAll(): Flow<List<StatusEntity>>

    @Query("SELECT * FROM statuses ORDER BY sortOrder ASC, name ASC")
    suspend fun all(): List<StatusEntity>

    @Upsert
    suspend fun upsert(status: StatusEntity)

    @Query("DELETE FROM statuses WHERE id = :id")
    suspend fun delete(id: String)
}
