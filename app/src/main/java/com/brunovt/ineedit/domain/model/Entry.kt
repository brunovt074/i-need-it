package com.brunovt.ineedit.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class Entry(
    val id: String,
    val column: Column,
    val name: String,
    val timeKey: TimeKey?,
    val specificDate: LocalDate?,
    val cost: Money?,
    val place: String?,
    val tags: List<Tag>,
    val status: Status?,
    val actions: List<Action>,
    val completedAt: Instant?,
    val createdAt: Instant,
    val updatedAt: Instant,
)
