package com.farshidabz.spnote.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshidabz.spnote.domain.notes.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
) : ViewModel() {

    private val query = MutableStateFlow("")

    private val _effects = MutableSharedFlow<HomeEffect>()
    val effects = _effects.asSharedFlow()

    val state: StateFlow<HomeState> = combine(
        query,
        notesRepository.getNotes(),
    ) { q, notes ->
        val filtered = if (q.isBlank()) notes else notes.filter {
            it.title.contains(q, ignoreCase = true) || it.body.contains(q, ignoreCase = true)
        }
        HomeState(
            query = q,
            notes = filtered.map { it.toUi() },
            isLoading = false,
            error = null,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnQueryChange -> query.value = event.query
            HomeEvent.Refresh -> refresh()
            HomeEvent.FabClicked -> emitEffect(HomeEffect.NavigateToCreate)
            is HomeEvent.NoteClicked -> emitEffect(HomeEffect.NavigateToDetail(event.id))
            HomeEvent.LoadInitial -> { /* handled by flows */ }
        }
    }

    private fun refresh() {
        // Placeholder for future repository refresh logic.
        // Could trigger sync or invalidate cache; for now no-op.
    }

    private fun emitEffect(effect: HomeEffect) {
        viewModelScope.launch { _effects.emit(effect) }
    }
}