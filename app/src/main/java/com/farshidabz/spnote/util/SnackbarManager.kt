package com.farshidabz.spnote.util

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * Centralized Snackbar manager to display messages across the app.
 * Emit events from any layer that depends on core-ui (e.g., feature view models),
 * and collect them at the top-level Scaffold to show snackbars.
 */
object SnackbarManager {
    private val _events = MutableSharedFlow<SnackbarEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<SnackbarEvent> = _events

    suspend fun show(message: String) {
        _events.emit(SnackbarEvent(message = message))
    }

    suspend fun show(messageResId: Int, actionLabelResId: Int? = null) {
        _events.emit(SnackbarEvent(messageResId = messageResId, actionLabelResId = actionLabelResId))
    }
}

/**
 * Event model for Snackbar.
 */
data class SnackbarEvent(
    val message: String? = null,
    val messageResId: Int? = null,
    val actionLabelResId: Int? = null
)
