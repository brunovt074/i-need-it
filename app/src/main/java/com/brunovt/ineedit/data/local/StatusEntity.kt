package com.brunovt.ineedit.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "statuses")
data class StatusEntity(
    @PrimaryKey val id: String,
    val name: String,
    val sortOrder: Int = 0,
)
