package com.farshidabz.spnote.presentation.feature.notedetail

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.ViewModel
import com.farshidabz.spnote.domain.notes.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(NoteState())
    val state = _state.asStateFlow()

    fun onIntent(intent: NoteIntent) {
        when (intent) {
            is NoteIntent.UpdateText -> handleTextUpdate(intent.value)
            is NoteIntent.ToggleActiveStyle -> {
                _state.update { it.copy(activeStyles = if (it.activeStyles.contains(intent.format)) it.activeStyles - intent.format else it.activeStyles + intent.format) }
            }

            is NoteIntent.UpdateBrush -> {
                _state.update { currentState ->
                    currentState.copy(
                        brushColor = intent.color ?: currentState.brushColor,
                        strokeWidth = intent.width ?: currentState.strokeWidth
                    )
                }
            }

            is NoteIntent.AddPath -> {
                _state.update { currentState ->
                    val newDrawingPath = DrawingPath(
                        path = intent.path,
                        color = currentState.brushColor,
                        strokeWidth = currentState.strokeWidth
                    )
                    currentState.copy(paths = currentState.paths + newDrawingPath)
                }
            }

            is NoteIntent.ConfirmStyles -> handleConfirm()
            is NoteIntent.ClearToDefault -> handleClear()
            is NoteIntent.SetSheetVisible -> _state.update { it.copy(isSheetVisible = intent.visible) }
            is NoteIntent.ToggleMode -> _state.update { it.copy(isDrawingMode = intent.isDrawing) }
            is NoteIntent.UpdateDimensions -> _state.update {
                it.copy(
                    viewportHeight = intent.viewportH ?: it.viewportHeight,
                    textContentHeight = intent.textH ?: it.textContentHeight
                )
            }

            is NoteIntent.UpdateTextColor -> {
                _state.update { currentState ->
                    val selection = currentState.textFieldValue.selection

                    val nextTextFieldValue = if (!selection.collapsed) {
                        buildAnnotatedString {
                            append(currentState.textFieldValue.annotatedString)
                            addStyle(
                                style = SpanStyle(color = intent.color),
                                start = selection.start,
                                end = selection.end
                            )
                        }.let { currentState.textFieldValue.copy(annotatedString = it) }
                    } else {
                        currentState.textFieldValue
                    }

                    currentState.copy(
                        activeTextColor = intent.color,
                        textFieldValue = nextTextFieldValue
                    )
                }
            }

            NoteIntent.ClearCanvas -> _state.update { it.copy(paths = emptyList()) }
            else -> {}
        }
    }

    private fun handleTextUpdate(newValue: TextFieldValue) {
        val oldState = _state.value
        val oldValue = oldState.textFieldValue

        if (newValue.text == oldValue.text) {
            _state.update { it.copy(textFieldValue = newValue.copy(annotatedString = oldValue.annotatedString)) }
            return
        }

        val lengthDiff = newValue.text.length - oldValue.text.length
        val processedValue = if (lengthDiff > 0) {
            val insertionPoint = (newValue.selection.start - lengthDiff).coerceAtLeast(0)
            buildAnnotatedString {
                append(newValue.text)
                // Restore old spans
                oldValue.annotatedString.spanStyles.forEach {
                    if (it.end <= newValue.text.length) addStyle(
                        it.item,
                        it.start,
                        it.end
                    )
                }

                // Apply Bold/Italic active styles
                oldState.activeStyles.forEach {
                    addStyle(
                        getSpanStyle(it),
                        insertionPoint,
                        insertionPoint + lengthDiff
                    )
                }

                // APPLY THE TEXT COLOR Span here
                addStyle(
                    SpanStyle(color = oldState.activeTextColor),
                    insertionPoint,
                    insertionPoint + lengthDiff
                )

            }.let { newValue.copy(annotatedString = it) }
        } else {
            // Deletion case
            buildAnnotatedString {
                append(newValue.text)
                oldValue.annotatedString.spanStyles.forEach {
                    if (it.end <= newValue.text.length) addStyle(
                        it.item,
                        it.start,
                        it.end
                    )
                }
            }.let { newValue.copy(annotatedString = it) }
        }
        _state.update { it.copy(textFieldValue = processedValue) }
    }

    private fun handleConfirm() {
        _state.update { s ->
            val sel = s.textFieldValue.selection
            val newString = if (!sel.collapsed) {
                buildAnnotatedString {
                    append(s.textFieldValue.annotatedString)
                    s.activeStyles.forEach { addStyle(getSpanStyle(it), sel.start, sel.end) }
                }
            } else s.textFieldValue.annotatedString

            s.copy(
                textFieldValue = s.textFieldValue.copy(annotatedString = newString),
                isSheetVisible = false
            )
        }
    }

    private fun handleClear() {
        _state.update { s ->
            val sel = s.textFieldValue.selection
            val clearedString = if (!sel.collapsed) {
                buildAnnotatedString {
                    append(s.textFieldValue.text)
                    s.textFieldValue.annotatedString.spanStyles.forEach {
                        if (it.end <= sel.start || it.start >= sel.end) addStyle(
                            it.item,
                            it.start,
                            it.end
                        )
                    }
                }
            } else s.textFieldValue.annotatedString
            s.copy(
                textFieldValue = s.textFieldValue.copy(annotatedString = clearedString),
                activeStyles = emptySet()
            )
        }
    }

    private fun getSpanStyle(f: TextFormat) = when (f) {
        TextFormat.BOLD -> SpanStyle(fontWeight = FontWeight.Bold)
        TextFormat.ITALIC -> SpanStyle(fontStyle = FontStyle.Italic)
        TextFormat.UNDERLINE -> SpanStyle(textDecoration = TextDecoration.Underline)
    }
}