package com.brunovt.ineedit.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Action(val id: String, val text: String, val checked: Boolean = false)
