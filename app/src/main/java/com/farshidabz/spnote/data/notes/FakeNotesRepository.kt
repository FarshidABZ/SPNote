package com.farshidabz.spnote.data.notes

import com.farshidabz.spnote.domain.notes.Note
import com.farshidabz.spnote.domain.notes.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeNotesRepository @Inject constructor() : NotesRepository {
    private val notesFlow = MutableStateFlow(
        listOf<Note>(
//            Note(1, "Groceries", "Milk\nEggs\nBread…\nMore…"),
//            Note(2, "", ""),
//            Note(3, "Meeting Notes", "Discuss Q4 goals to fire-starting months."),
//            Note(4, "Meeting Notes", "Milk, Eggs"),
//            Note(5, "Meeting Notes", "Discuss Q4 goals to fire-starting months."),
//            Note(6, "Groceries", "Milk, Eggs, Bread …, and check out row and inn brains …"),
//            Note(7, "", ""),
        )
    )

    override fun getNotes(): Flow<List<Note>> = notesFlow.asStateFlow()
}