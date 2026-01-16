package com.farshidabz.spnote.presentation.feature.notedetail

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
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

            is NoteIntent.SetDrawingMode -> _state.update {
                Log.e("Farshid", "SetDrawingMode: ${intent.mode}")
                it.copy(drawingMode = intent.mode)
            }

            is NoteIntent.EraseAt -> handleEraser(intent.offset)

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
                    val isEraserMode = currentState.drawingMode == DrawingMode.ERASER

                    val newStroke = DrawingPath(
                        path = intent.path,
                        // Eraser color doesn't matter when using BlendMode.Clear, but Transparent is safe
                        color = if (isEraserMode) Color.Transparent else currentState.brushColor,
                        // Eraser is usually wider for better UX
                        width = if (isEraserMode) 50f else currentState.strokeWidth,
                        isEraser = isEraserMode
                    )

                    currentState.copy(paths = currentState.paths + newStroke)
                }
            }

            is NoteIntent.ConfirmStyles -> handleConfirm()
            is NoteIntent.SetPaperSheetVisible -> _state.update { it.copy(isPaperSheetVisible = intent.visible) }
            is NoteIntent.UpdatePaperStyle -> _state.update { it.copy(paperStyle = intent.style) }
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

    private fun handleEraser(offset: Offset) {
        Log.e("Farshid", "handleEraser: $offset")
        _state.update { currentState ->
            // Filter out paths that are "hit" by the eraser point
            val remainingPaths = currentState.paths.filterNot { drawingPath ->
                isPointNearPath(offset, drawingPath.path, currentState.eraserSensitivity)
            }

            // Only update state if something actually was erased (performance optimization)
            if (remainingPaths.size != currentState.paths.size) {
                currentState.copy(paths = remainingPaths)
            } else {
                currentState
            }
        }
    }

    private fun isPointNearPath(point: Offset, path: Path, threshold: Float): Boolean {
        // We convert the Compose Path to an Android Path to use PathMeasure
        val androidPath = path.asAndroidPath()
        val pm = android.graphics.PathMeasure(androidPath, false)
        val pos = floatArrayOf(0f, 0f)
        val pathLength = pm.length

        // Performance: Check every 12 pixels along the path
        val step = 12f
        var distance = 0f

        while (distance < pathLength) {
            pm.getPosTan(distance, pos, null)
            val dx = point.x - pos[0]
            val dy = point.y - pos[1]

            // Basic Pythagorean distance check
            if (Math.sqrt((dx * dx + dy * dy).toDouble()) < threshold) {
                return true
            }
            distance += step
        }
        return false
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