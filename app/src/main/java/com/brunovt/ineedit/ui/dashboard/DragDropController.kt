package com.brunovt.ineedit.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import com.brunovt.ineedit.domain.model.Column
import com.brunovt.ineedit.domain.model.Entry

class DragDropController {
    var isDragging by mutableStateOf(false)
        private set
    var draggingEntry by mutableStateOf<Entry?>(null)
        private set
    var dragPosition by mutableStateOf(Offset.Zero)
        private set
    var hoveredColumn by mutableStateOf<Column?>(null)
        private set

    private val dropZoneRects = mutableMapOf<Column, Rect>()

    fun startDrag(entry: Entry, position: Offset) {
        draggingEntry = entry
        dragPosition = position
        isDragging = true
        hoveredColumn = null
    }

    fun onDragDelta(delta: Offset) {
        dragPosition += delta
        hoveredColumn = dropZoneRects.entries
            .firstOrNull { (_, rect) -> rect.contains(dragPosition) }
            ?.key
    }

    fun endDrag(): Pair<Entry, Column>? {
        val entry = draggingEntry
        val col = hoveredColumn
        reset()
        return if (entry != null && col != null) entry to col else null
    }

    fun cancelDrag() = reset()

    fun registerDropZone(column: Column, rect: Rect) {
        dropZoneRects[column] = rect
    }

    private fun reset() {
        isDragging = false
        draggingEntry = null
        dragPosition = Offset.Zero
        hoveredColumn = null
    }
}

val LocalDragDrop = compositionLocalOf<DragDropController?> { null }

@Composable
fun rememberDragDropController(): DragDropController = remember { DragDropController() }
