package com.farshidabz.spnote.presentation.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farshidabz.spnote.R
import com.farshidabz.spnote.designsystem.component.SearchBar
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onOpenNote: (Int) -> Unit = {},
    onCreateNote: () -> Unit = {},
) {
    HomeScreen(
        modifier = modifier,
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onEvent = viewModel::onEvent,
        onEffect = { effect ->
            when (effect) {
                is HomeEffect.NavigateToDetail -> onOpenNote(effect.id)
                HomeEffect.NavigateToCreate -> onCreateNote()
                is HomeEffect.ShowMessage -> { /* TODO: Snackbar */
                }
            }
        },
        effects = viewModel.effects,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    onEffect: (HomeEffect) -> Unit,
    effects: Flow<HomeEffect>,
) {
    LaunchedEffect(Unit) {
        onEvent(HomeEvent.LoadInitial)
        effects.collect { onEffect(it) }
    }
    if (state.isEmpty) {
        EmptyStateContent()
    } else {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { onEvent(HomeEvent.FabClicked) },
                    icon = { Text("+") },
                    text = { Text(stringResource(id = R.string.create_caps)) }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .consumeWindowInsets(innerPadding)
                    .padding(horizontal = 16.dp),
            ) {
                Spacer(Modifier.height(8.dp))
                SearchBar(
                    query = state.query,
                    onQueryChange = { onEvent(HomeEvent.OnQueryChange(it)) }
                )
                Spacer(Modifier.height(12.dp))
                LazyVerticalStaggeredGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 12.dp,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 88.dp)
                ) {
                    items(state.notes, key = { it.id }) { note ->
                        NoteCard(note = note, onClick = { onEvent(HomeEvent.NoteClicked(note.id)) })
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStateContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EmptyState()
        Button(
            onClick = {},
            modifier = Modifier
                .padding(top = 24.dp)
                .height(64.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_add_white),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.new_note),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun EmptyState() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                contentScale = ContentScale.Crop
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                painter = painterResource(R.drawable.note_empty_state),
                contentDescription = null,
            )
        }

        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.no_note_availabe),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.tap_plus_to_new_note),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun NoteCard(
    note: NoteUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (note.title.isNotBlank()) {
                Text(
                    text = note.title + ":",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(8.dp))
            }
            if (note.body.isNotBlank()) {
                Text(
                    text = note.body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (note.title.isBlank() && note.body.isBlank()) {
                Text(
                    text = "",
                    modifier = Modifier.height(120.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(modifier: Modifier = Modifier) {
    // Simple preview using static data
    val previewState = HomeState(
        query = "",
        notes = listOf(
            NoteUi(1, "Groceries", "Milk\nEggs\nBread…\nMore…"),
            NoteUi(2, "Meeting Notes", "Discuss Q4 goals to fire-starting months."),
            NoteUi(3, "Meeting Notes", "Discuss Q4 goals to fire-starting months."),
            NoteUi(4, "Meeting Notes", "Discuss Q4 goals to fire-starting months."),
            NoteUi(5, "Meeting Notes", "Discuss Q4 goals to fire-starting months."),
            NoteUi(6, "Meeting Notes", "Discuss Q4 goals to fire-starting months."),
            NoteUi(7, "Meeting Notes", "Discuss Q4 goals to fire-starting months."),
            NoteUi(8, "Meeting Notes", "Discuss Q4 goals to fire-starting months."),
            NoteUi(9, "Meeting Notes", "Discuss Q4 goals to fire-starting months."),
            NoteUi(10, "Meeting Notes", "Discuss Q4"),
            NoteUi(11, "Meeting Notes", "goals to fire-starting."),
            NoteUi(12, "Meeting Notes", ""),
            NoteUi(13, "Meeting Notes", "Discuss Q4 goals to fire-starting months."),
            NoteUi(14, "Meeting Notes", "Discuss Q4 goals to fire-starting months."),
            NoteUi(15, "Meeting Notes", "Discuss Q4 goals to fire-starting months."),
        )
    )
    MaterialTheme {
        HomeScreen(
            modifier = modifier,
            state = previewState,
            onEvent = {},
            onEffect = {},
            effects = kotlinx.coroutines.flow.emptyFlow(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreviewEmpty(modifier: Modifier = Modifier) {
    val previewState = HomeState(
        query = "",
        notes = emptyList()
    )
    MaterialTheme {
        HomeScreen(
            modifier = modifier,
            state = previewState,
            onEvent = {},
            onEffect = {},
            effects = kotlinx.coroutines.flow.emptyFlow(),
        )
    }
}
