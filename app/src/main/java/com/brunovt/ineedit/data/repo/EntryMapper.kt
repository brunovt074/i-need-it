package com.brunovt.ineedit.data.repo

import com.brunovt.ineedit.data.local.EntryEntity
import com.brunovt.ineedit.domain.model.Action
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry
import com.brunovt.ineedit.domain.model.Money
import com.brunovt.ineedit.domain.model.Status
import com.brunovt.ineedit.domain.model.Tag
import com.brunovt.ineedit.domain.model.TimeKey
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

fun EntryEntity.toDomain(
    tagMap: Map<String, Tag>,
    statusMap: Map<String, Status>,
): Entry {
    val tagIds = if (tags.isBlank() || tags == "[]") emptyList()
    else json.decodeFromString(ListSerializer(String.serializer()), tags)

    return Entry(
        id = id,
        column = Column.valueOf(column),
        name = name,
        timeKey = timeKey?.let { TimeKey.valueOf(it) },
        specificDate = specificDate?.let { LocalDate.parse(it) },
        cost = if (costAmountMinor != null && costCurrency != null)
            Money(costAmountMinor, costCurrency) else null,
        place = place,
        tags = tagIds.mapNotNull { tagMap[it] },
        status = statusId?.let { statusMap[it] },
        actions = actions.let {
            if (it.isBlank() || it == "[]") emptyList()
            else json.decodeFromString(ListSerializer(Action.serializer()), it)
        },
        completedAt = completedAt?.let { Instant.fromEpochSeconds(it) },
        createdAt = Instant.fromEpochSeconds(createdAt),
        updatedAt = Instant.fromEpochSeconds(updatedAt),
    )
}

fun Entry.toEntity(): EntryEntity = EntryEntity(
    id = id,
    column = column.name,
    name = name,
    timeKey = timeKey?.name,
    specificDate = specificDate?.toString(),
    costAmountMinor = cost?.amountMinor,
    costCurrency = cost?.currency,
    place = place,
    tags = json.encodeToString(ListSerializer(String.serializer()), tags.map { it.id }),
    statusId = status?.id,
    actions = json.encodeToString(ListSerializer(Action.serializer()), actions),
    completedAt = completedAt?.epochSeconds,
    createdAt = createdAt.epochSeconds,
    updatedAt = updatedAt.epochSeconds,
)
