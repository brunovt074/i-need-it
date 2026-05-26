package com.brunovt.ineedit.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE entries ADD COLUMN statusId TEXT")
        db.execSQL("ALTER TABLE entries ADD COLUMN actions TEXT NOT NULL DEFAULT '[]'")
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS tags (
                id TEXT NOT NULL,
                name TEXT NOT NULL,
                sortOrder INTEGER NOT NULL,
                PRIMARY KEY(id)
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS statuses (
                id TEXT NOT NULL,
                name TEXT NOT NULL,
                sortOrder INTEGER NOT NULL,
                PRIMARY KEY(id)
            )
            """.trimIndent()
        )
    }
}
