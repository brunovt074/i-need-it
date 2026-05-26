package com.brunovt.ineedit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [EntryEntity::class, TagEntity::class, StatusEntity::class],
    version = 2,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao
    abstract fun tagDao(): TagDao
    abstract fun statusDao(): StatusDao
}
