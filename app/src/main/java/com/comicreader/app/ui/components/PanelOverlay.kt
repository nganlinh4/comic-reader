package com.comicreader.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.comicreader.app.data.ComicPanel
import com.comicreader.app.data.PanelBounds

@Composable
fun PanelOverlay(
    panels: List<ComicPanel>,
    currentPanelIndex: Int?,
    imageSize: Size,
    modifier: Modifier = Modifier,
    showAllPanels: Boolean = false
) {
    val density = LocalDensity.current
    val strokeWidth = with(density) { 3.dp.toPx() }

    val highlightAlpha by animateFloatAsState(
        targetValue = if (currentPanelIndex != null) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.8f),
        label = "panel_highlight_alpha"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        if (imageSize.width > 0 && imageSize.height > 0) {
            // Calculate the actual image bounds within the canvas
            val imageBounds = calculateImageBounds(size, imageSize)

            panels.forEachIndexed { index, panel ->
                val shouldHighlight = currentPanelIndex == index
                val shouldShow = showAllPanels || shouldHighlight

                if (shouldShow) {
                    drawPanelBorder(
                        panel = panel,
                        imageBounds = imageBounds,
                        isHighlighted = shouldHighlight,
                        alpha = if (shouldHighlight) highlightAlpha else 0.3f,
                        strokeWidth = strokeWidth
                    )
                }
            }
        }
    }
}

private fun calculateImageBounds(canvasSize: Size, imageSize: Size): Rect {
    val canvasAspectRatio = canvasSize.width / canvasSize.height
    val imageAspectRatio = imageSize.width / imageSize.height

    return if (imageAspectRatio > canvasAspectRatio) {
        // Image is wider, fit to width
        val scaledHeight = canvasSize.width / imageAspectRatio
        val offsetY = (canvasSize.height - scaledHeight) / 2f
        Rect(
            offset = Offset(0f, offsetY),
            size = Size(canvasSize.width, scaledHeight)
        )
    } else {
        // Image is taller, fit to height
        val scaledWidth = canvasSize.height * imageAspectRatio
        val offsetX = (canvasSize.width - scaledWidth) / 2f
        Rect(
            offset = Offset(offsetX, 0f),
            size = Size(scaledWidth, canvasSize.height)
        )
    }
}

private fun DrawScope.drawPanelBorder(
    panel: ComicPanel,
    imageBounds: Rect,
    isHighlighted: Boolean,
    alpha: Float,
    strokeWidth: Float
) {
    val bounds = panel.bounds

    // Convert percentage bounds to actual pixel coordinates
    val left = imageBounds.left + (bounds.left * imageBounds.width)
    val top = imageBounds.top + (bounds.top * imageBounds.height)
    val right = imageBounds.left + (bounds.right * imageBounds.width)
    val bottom = imageBounds.top + (bounds.bottom * imageBounds.height)

    val color = if (isHighlighted) {
        Color.Red.copy(alpha = alpha)
    } else {
        Color.White.copy(alpha = alpha)
    }

    // Draw panel border
    drawRect(
        color = color,
        topLeft = Offset(left, top),
        size = Size(right - left, bottom - top),
        style = Stroke(width = strokeWidth)
    )

    // Draw panel number if highlighted
    if (isHighlighted && alpha > 0.5f) {
        drawCircle(
            color = Color.Red.copy(alpha = alpha),
            radius = 20f,
            center = Offset(left + 30f, top + 30f)
        )
        // Note: Text drawing would require TextPainter or similar
        // For simplicity, we'll just use the circle as an indicator
    }
}

/**
 * Utility function to find which panel contains a given tap point
 */
fun findPanelAtPoint(
    panels: List<ComicPanel>,
    tapPoint: Offset,
    canvasSize: Size,
    imageSize: Size
): Int? {
    if (imageSize.width <= 0 || imageSize.height <= 0) return null

    val imageBounds = calculateImageBounds(canvasSize, imageSize)

    // Check if tap is within image bounds
    if (!imageBounds.contains(tapPoint)) return null

    // Convert tap point to image coordinates (0.0 to 1.0)
    val relativeX = (tapPoint.x - imageBounds.left) / imageBounds.width
    val relativeY = (tapPoint.y - imageBounds.top) / imageBounds.height

    // Find the panel that contains this point
    return panels.indexOfFirst { panel ->
        val bounds = panel.bounds
        relativeX >= bounds.left && relativeX <= bounds.right &&
        relativeY >= bounds.top && relativeY <= bounds.bottom
    }.takeIf { it >= 0 }
}

