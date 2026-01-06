package com.farshidabz.spnote.presentation.feature.notedetail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farshidabz.spnote.R
import com.farshidabz.spnote.designsystem.theme.SPNoteTheme
import kotlinx.coroutines.delay

@Composable
fun NoteDetailScreenRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: NoteDetailViewModel = hiltViewModel<NoteDetailViewModel>()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    NoteDetailScreen(modifier, uiState, viewModel::onIntent, onBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    modifier: Modifier = Modifier,
    state: NoteState,
    onIntent: (NoteIntent) -> Unit,
    onBack: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    val density = LocalDensity.current
    val sheetState = rememberModalBottomSheetState()

    // Local UI state for "Hot" path (Digital Ink) - Redraws on trigger
    var activePath by remember { mutableStateOf<Path?>(null) }
    var drawTrigger by remember { mutableStateOf(0) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    // --- Side Effects: Scroll & Focus ---
    LaunchedEffect(state.textFieldValue.selection, state.viewportHeight) {
        val layout = textLayoutResult ?: return@LaunchedEffect
        val selectionIndex = state.textFieldValue.selection.start
        if (selectionIndex <= layout.layoutInput.text.length) {
            val cursorRect = layout.getCursorRect(selectionIndex)
            val threshold = state.viewportHeight * (2 / 3f)
            if (state.textContentHeight > threshold) {
                val triggerPoint = scrollState.value + state.viewportHeight - 100
                if (cursorRect.bottom > triggerPoint) {
                    val target =
                        cursorRect.bottom - state.viewportHeight + (state.viewportHeight / 4)
                    scrollState.animateScrollTo(target.toInt().coerceAtMost(scrollState.maxValue))
                }
            }
        }
    }

    LaunchedEffect(state.isDrawingMode) {
        if (state.isDrawingMode) {
            focusManager.clearFocus()
            keyboardController?.hide()
        } else {
            delay(50)
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    Scaffold(
        topBar = {
            NoteTopAppBar(
                state.isDrawingMode,
                onBack = onBack,
                onToggle = { onIntent(NoteIntent.ToggleMode(!state.isDrawingMode)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onIntent(NoteIntent.SetSheetVisible(true)) }) {
                Icon(
                    imageVector = ImageVector.vectorResource(if (state.isDrawingMode) R.drawable.ic_brush else R.drawable.ic_textfield),
                    contentDescription = "Format"
                )
            }
        }
    ) { padding ->
        if (state.isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .onGloballyPositioned { onIntent(NoteIntent.UpdateDimensions(viewportH = it.size.height)) }
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = with(density) { state.viewportHeight.toDp() })
                        .drawBehind {
                            // Reads drawTrigger to force a redraw when finger moves
                            val _t = drawTrigger

                            // Draw all saved paths with their OWN properties
                            state.paths.forEach { item ->
                                drawPath(
                                    path = item.path,
                                    color = item.color,
                                    style = Stroke(
                                        width = item.strokeWidth,
                                        cap = StrokeCap.Round,
                                        join = StrokeJoin.Round
                                    )
                                )
                            }

                            // Draw the active path with CURRENT state properties
                            activePath?.let {
                                drawPath(
                                    path = it,
                                    color = state.brushColor,
                                    style = Stroke(
                                        width = state.strokeWidth,
                                        cap = StrokeCap.Round,
                                        join = StrokeJoin.Round
                                    )
                                )
                            }
                        }
                        .pointerInput(state.isDrawingMode) {
                            if (!state.isDrawingMode) return@pointerInput
                            detectDragGestures(
                                onDragStart = { offset ->
                                    activePath = Path().apply { moveTo(offset.x, offset.y) }
                                },
                                onDrag = { change, _ ->
                                    change.consume()
                                    activePath?.lineTo(change.position.x, change.position.y)
                                    drawTrigger++
                                },
                                onDragEnd = {
                                    activePath?.let {
                                        // Just send the path; the VM handles the rest
                                        onIntent(NoteIntent.AddPath(it))
                                    }
                                    activePath = null
                                }
                            )
                        }
                ) {
                    BasicTextField(
                        value = state.textFieldValue,
                        onValueChange = { onIntent(NoteIntent.UpdateText(it)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .focusRequester(focusRequester)
                            .onGloballyPositioned { onIntent(NoteIntent.UpdateDimensions(textH = it.size.height)) },
                        onTextLayout = { textLayoutResult = it },
                        enabled = !state.isDrawingMode,
                        textStyle = TextStyle(
                            fontSize = state.fontSize.sp,
                            color = state.activeTextColor
                        )
                    )

                    if (state.textContentHeight > state.viewportHeight * (2 / 3f)) {
                        Spacer(modifier = Modifier.height(with(density) { (state.viewportHeight / 3f).toDp() }))
                    }
                }
            }
        }

        if (state.isSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = { onIntent(NoteIntent.SetSheetVisible(false)) },
                sheetState = sheetState
            ) {
                if (state.isDrawingMode) {
                    DrawingToolsContent(state, onIntent)
                } else {
                    FormattingToolsContent(state, onIntent)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopAppBar(isDrawingMode: Boolean, onBack: () -> Unit, onToggle: () -> Unit) {
    TopAppBar(
        title = { Text("Note Title", style = MaterialTheme.typography.titleLarge) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    ImageVector.vectorResource(R.drawable.ic_arrow_back_white),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        actions = {
            // Mode Toggle Switch or Iconic Button
            IconButton(onClick = onToggle) {
                Icon(
                    imageVector = ImageVector.vectorResource(if (isDrawingMode) R.drawable.ic_pencil else R.drawable.ic_brush),
                    tint = if (isDrawingMode) MaterialTheme.colorScheme.primary else LocalContentColor.current,
                    contentDescription = "Switch Mode"
                )
            }
        }
    )
}

@Composable
fun DrawingToolsContent(state: NoteState, onIntent: (NoteIntent) -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .navigationBarsPadding()
    ) {
        Text("Brush Thickness", style = MaterialTheme.typography.labelMedium)
        Slider(
            value = state.strokeWidth,
            onValueChange = { onIntent(NoteIntent.UpdateBrush(width = it)) },
            valueRange = 1f..50f
        )

        Spacer(Modifier.height(16.dp))

        Text("Colors", style = MaterialTheme.typography.labelMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Black).forEach { color ->
                ColorCircle(
                    color = color,
                    isSelected = state.brushColor == color,
                    onClick = { onIntent(NoteIntent.UpdateBrush(color = color)) }
                )
            }
        }
    }
}

@Composable
fun ColorCircle(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animate the border thickness for a smoother feel when selecting
    val borderThickness by animateDpAsState(
        targetValue = if (isSelected) 3.dp else 0.dp,
        label = "BorderSelection"
    )

    Box(
        modifier = modifier
            .size(40.dp) // Standard touch target size
            .clip(CircleShape)
            .background(color)
            .border(
                width = borderThickness,
                color = if (color == Color.Black) Color.Gray else Color.Black,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
    )
}

@Composable
fun FormatToggleButton(
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFE0F2F1) else Color.Transparent,
        label = "BgAnimation"
    )

    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = backgroundColor,
        modifier = modifier.size(height = 56.dp, width = 80.dp) // Pill shape
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color(0xFF004D40) else Color.Black
            )
        }
    }
}

@Composable
fun FormattingToolsContent(state: NoteState, onIntent: (NoteIntent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Format",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            TextFormat.entries.forEach { format ->
                FormatToggleButton(
                    icon = when (format) {
                        TextFormat.BOLD -> ImageVector.vectorResource(R.drawable.ic_bold)
                        TextFormat.ITALIC -> ImageVector.vectorResource(R.drawable.ic_italic)
                        TextFormat.UNDERLINE -> ImageVector.vectorResource(R.drawable.ic_underline)
                    },
                    isSelected = state.activeStyles.contains(format),
                    onClick = { onIntent(NoteIntent.ToggleActiveStyle(format)) }
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        Text("Text Color", style = MaterialTheme.typography.labelMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf(Color(0xFF4DB6AC), Color(0xFFFFB74D), Color.Black).forEach { color ->
                ColorCircle(
                    color = color,
                    isSelected = state.activeTextColor == color, // Checks text color
                    onClick = { onIntent(NoteIntent.UpdateTextColor(color)) }
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // Confirm / Clear Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { onIntent(NoteIntent.ClearToDefault) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Clear All")
            }
            Button(
                onClick = { onIntent(NoteIntent.ConfirmStyles) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Confirm")
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_9_PRO,
)
@Composable
private fun NoteDetailPreview() {
    SPNoteTheme {
        NoteDetailScreen(
            state = NoteState(
                isLoading = false,
                isDrawingMode = false,
                paths = emptyList(),
                textFieldValue = TextFieldValue("Hello World!"),
            ),
            onBack = {},
            onIntent = {}
        )
    }
}