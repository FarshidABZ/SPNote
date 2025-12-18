package com.farshidabz.spnote.domain.notes

import kotlinx.coroutines.flow.Flow

/**
 * Repository abstraction for notes.
 */
interface NotesRepository {
    /**
     * Returns stream of all notes. In a real implementation this could be backed by DB or network.
     */
    fun getNotes(): Flow<List<Note>>
}

/**
 * Domain model for a Note.
 */
data class Note(
    val id: Int,
    val title: String,
    val body: String,
)