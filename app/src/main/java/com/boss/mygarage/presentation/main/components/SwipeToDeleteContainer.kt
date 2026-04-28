package com.boss.mygarage.presentation.main.components

import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.boss.mygarage.R
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeToDeleteContainer(
    deleteDialogTitle: String = stringResource(R.string.delete_button_title),
    deleteDialogText: String = "",
    onDeleteClick: () -> Unit,
    content: @Composable () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = DragValue.Center
        )
    }

    // 2. Animation behavior
    val flingBehavior = AnchoredDraggableDefaults.flingBehavior(
        state = anchoredDraggableState,
        positionalThreshold = { distance -> distance * 0.5f },
        animationSpec = spring(),
    )

    LaunchedEffect(anchoredDraggableState.targetValue) {
        if (anchoredDraggableState.targetValue == DragValue.End) {
            showDeleteDialog = true
        }
    }

    LaunchedEffect(showDeleteDialog) {
        if (!showDeleteDialog && anchoredDraggableState.currentValue == DragValue.End) {
            anchoredDraggableState.animateTo(DragValue.Center)
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteClick()
                    showDeleteDialog = false
                }) {
                    Text(
                        stringResource(R.string.delete_button_title),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                }) { Text(stringResource(R.string.cancel_button_title)) }
            },
            title = { Text( deleteDialogTitle ) },
            text = { Text ( deleteDialogText ) }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .onSizeChanged { size ->
                val dragRange = size.width.toFloat()
                val newAnchors = DraggableAnchors {
                    DragValue.Center at 0f
                    DragValue.End at -dragRange
                }
                anchoredDraggableState.updateAnchors(newAnchors)
            }
            .anchoredDraggable(
                state = anchoredDraggableState,
                orientation = Orientation.Horizontal,
                flingBehavior = flingBehavior
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CardDefaults.shape)
                .background(MaterialTheme.colorScheme.errorContainer)
                .padding(end = 24.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(anchoredDraggableState.offset.roundToInt(), 0) }
                .fillMaxSize()
        ) {
            content()
        }
    }
}
