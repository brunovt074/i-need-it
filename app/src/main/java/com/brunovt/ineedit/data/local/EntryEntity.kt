package com.brunovt.ineedit.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class EntryEntity(
    @PrimaryKey val id: String,
    val column: String,
    val name: String,
    val timeKey: String?,
    val specificDate: String?,
    val costAmountMinor: Long?,
    val costCurrency: String?,
    val place: String?,
    val tags: String,
    val statusId: String?,
    val actions: String,
    val completedAt: Long?,
    val createdAt: Long,
    val updatedAt: Long,
)
