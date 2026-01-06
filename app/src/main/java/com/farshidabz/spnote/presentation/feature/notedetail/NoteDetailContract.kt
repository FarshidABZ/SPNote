package com.farshidabz.spnote.presentation.feature.notedetail

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.input.TextFieldValue

data class DrawingPath(
    val path: Path,
    val color: Color = Color.Black,
    val strokeWidth: Float = 5f
)

enum class PaperStyle { SIMPLE, LINE, GRID }
enum class TextFormat { BOLD, ITALIC, UNDERLINE }

data class NoteState(
    val isLoading: Boolean = false,
    val textFieldValue: TextFieldValue = TextFieldValue(""),
    val activeStyles: Set<TextFormat> = emptySet(),
    val isDrawingMode: Boolean = false,
    val isSheetVisible: Boolean = false,
    val fontSize: Float = 16f,
    val paths: List<DrawingPath> = emptyList(),
    val viewportHeight: Int = 0,
    val textContentHeight: Int = 0,
    val strokeWidth: Float = 5f,
    val brushColor: Color = Color.Black,
    val activeTextColor: Color = Color.Black,
    val paperStyle: PaperStyle = PaperStyle.SIMPLE
)

sealed class NoteIntent {
    data class UpdateText(val value: TextFieldValue) : NoteIntent()
    data class ToggleMode(val isDrawing: Boolean) : NoteIntent()
    data class ToggleActiveStyle(val format: TextFormat) : NoteIntent()
    data class UpdateDimensions(val viewportH: Int? = null, val textH: Int? = null) : NoteIntent()
    data class SetSheetVisible(val visible: Boolean) : NoteIntent()
    data class AddPath(val path: Path) : NoteIntent()
    data class UpdateBrush(val color: Color? = null, val width: Float? = null) : NoteIntent()
    data class UpdateTextColor(val color: Color) : NoteIntent()
    data class UpdatePaperStyle(val style: PaperStyle) : NoteIntent()
    data object ConfirmStyles : NoteIntent()
    data object ClearToDefault : NoteIntent()
    data object ClearCanvas : NoteIntent()
    data object SaveNote : NoteIntent()
}