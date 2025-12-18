package com.farshidabz.spnote.presentation.feature.home

import com.farshidabz.spnote.domain.notes.Note

data class NoteUi(
    val id: Int,
    val title: String,
    val body: String,
)

// MVI State
data class HomeState(
    val query: String = "",
    val notes: List<NoteUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val isEmpty: Boolean get() = !isLoading && error == null && notes.isEmpty()
}

// User intents
typealias NoteId = Int

sealed interface HomeEvent {
    data class OnQueryChange(val query: String) : HomeEvent
    data object Refresh : HomeEvent
    data object FabClicked : HomeEvent
    data class NoteClicked(val id: NoteId) : HomeEvent
    data object LoadInitial : HomeEvent
}

// One-off effects (navigation, toasts)
sealed interface HomeEffect {
    data object NavigateToCreate : HomeEffect
    data class NavigateToDetail(val id: NoteId) : HomeEffect
    data class ShowMessage(val message: String) : HomeEffect
}

// Mapper
fun Note.toUi() = NoteUi(id = id, title = title, body = body)
