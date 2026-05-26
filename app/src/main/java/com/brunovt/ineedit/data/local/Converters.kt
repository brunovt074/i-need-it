package com.brunovt.ineedit.data.local

import androidx.room.TypeConverter
import com.brunovt.ineedit.domain.model.Action
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class Converters {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromStringList(value: List<String>): String =
        json.encodeToString(ListSerializer(String.serializer()), value)

    @TypeConverter
    fun toStringList(value: String): List<String> =
        if (value.isBlank() || value == "[]") emptyList()
        else json.decodeFromString(ListSerializer(String.serializer()), value)

    @TypeConverter
    fun fromActionList(value: List<Action>): String =
        json.encodeToString(ListSerializer(Action.serializer()), value)

    @TypeConverter
    fun toActionList(value: String): List<Action> =
        if (value.isBlank() || value == "[]") emptyList()
        else json.decodeFromString(ListSerializer(Action.serializer()), value)
}
