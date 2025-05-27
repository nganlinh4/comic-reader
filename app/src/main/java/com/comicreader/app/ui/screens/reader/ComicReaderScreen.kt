package com.comicreader.app.ui.screens.reader

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material.icons.filled.ViewQuilt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.comicreader.app.data.ReadingMode
import com.comicreader.app.data.SampleComicData
import com.comicreader.app.ui.components.PanelNavigationState
import com.comicreader.app.ui.components.PanelOverlay
import com.comicreader.app.ui.components.findPanelAtPoint
import com.comicreader.app.ui.components.rememberPanelNavigationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ComicReaderScreen(
    comicId: String,
    onBackClick: () -> Unit
) {
    val comic = remember { SampleComicData.sampleComics.find { it.id == comicId } }

    if (comic == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Comic not found")
        }
        return
    }

    val panelNavState = rememberPanelNavigationState(
        comic = comic,
        initialPageIndex = comic.currentPage
    )

    val pagerState = rememberPagerState(
        initialPage = panelNavState.currentPageIndex,
        pageCount = { comic.pages.size }
    )

    var showUI by remember { mutableStateOf(true) }
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var imageSize by remember { mutableStateOf(Size.Zero) }
    var canvasSize by remember { mutableStateOf(Size.Zero) }
    val coroutineScope = rememberCoroutineScope()

    // Sync pager state with panel navigation state
    LaunchedEffect(panelNavState.currentPageIndex) {
        if (pagerState.currentPage != panelNavState.currentPageIndex) {
            pagerState.animateScrollToPage(panelNavState.currentPageIndex)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != panelNavState.currentPageIndex) {
            panelNavState.goToPage(pagerState.currentPage)
        }
    }

    // Auto-hide UI after 3 seconds
    LaunchedEffect(showUI) {
        if (showUI) {
            delay(3000)
            showUI = false
        }
    }

    val transformableState = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 5f)
        offset = if (scale > 1f) {
            offset + offsetChange
        } else {
            Offset.Zero
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Comic Pages
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .transformable(state = transformableState)
                .onSizeChanged { canvasSize = it.toSize() }
                .pointerInput(panelNavState.readingMode, panelNavState.currentPageIndex) {
                    detectTapGestures(
                        onTap = { tapOffset ->
                            when (panelNavState.readingMode) {
                                ReadingMode.PANEL -> {
                                    val currentPage = panelNavState.currentPage
                                    if (currentPage != null && currentPage.panels.isNotEmpty()) {
                                        val panelIndex = findPanelAtPoint(
                                            panels = currentPage.panels,
                                            tapPoint = tapOffset,
                                            canvasSize = canvasSize,
                                            imageSize = imageSize
                                        )

                                        if (panelIndex != null) {
                                            panelNavState.goToPanel(panelNavState.currentPageIndex, panelIndex)
                                        } else {
                                            // Tap outside panels, toggle UI
                                            showUI = !showUI
                                        }
                                    } else {
                                        showUI = !showUI
                                    }
                                }
                                ReadingMode.PAGE -> {
                                    val screenWidth = size.width
                                    when {
                                        tapOffset.x < screenWidth * 0.3f -> {
                                            // Previous page
                                            panelNavState.previousPage()
                                        }
                                        tapOffset.x > screenWidth * 0.7f -> {
                                            // Next page
                                            panelNavState.nextPage()
                                        }
                                        else -> {
                                            // Toggle UI
                                            showUI = !showUI
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
        ) { page ->
            val comicPage = comic.pages[page]

            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = comicPage.imageUrl,
                    contentDescription = "Page ${comicPage.pageNumber}",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        ),
                    contentScale = ContentScale.Fit,
                    onState = { state ->
                        if (state is AsyncImagePainter.State.Success) {
                            val drawable = state.result.drawable
                            imageSize = Size(
                                drawable.intrinsicWidth.toFloat(),
                                drawable.intrinsicHeight.toFloat()
                            )
                        }
                    }
                )

                // Panel overlay for panel reading mode
                if (panelNavState.readingMode == ReadingMode.PANEL &&
                    page == panelNavState.currentPageIndex &&
                    comicPage.panels.isNotEmpty()) {
                    PanelOverlay(
                        panels = comicPage.panels,
                        currentPanelIndex = panelNavState.currentPanelIndex,
                        imageSize = imageSize,
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                translationX = offset.x,
                                translationY = offset.y
                            )
                    )
                }
            }
        }

        // Top App Bar
        AnimatedVisibility(
            visible = showUI,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = comic.title,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    // Reading mode toggle
                    IconButton(
                        onClick = { panelNavState.toggleReadingMode() }
                    ) {
                        Icon(
                            imageVector = when (panelNavState.readingMode) {
                                ReadingMode.PAGE -> Icons.Default.ViewQuilt
                                ReadingMode.PANEL -> Icons.Default.ViewModule
                            },
                            contentDescription = when (panelNavState.readingMode) {
                                ReadingMode.PAGE -> "Switch to Panel Mode"
                                ReadingMode.PANEL -> "Switch to Page Mode"
                            },
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = { /* TODO: Bookmark */ }) {
                        Icon(
                            Icons.Default.Bookmark,
                            contentDescription = "Bookmark",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { /* TODO: Settings */ }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.7f)
                )
            )
        }

        // Bottom Controls
        AnimatedVisibility(
            visible = showUI,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.8f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Reading mode indicator
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = when (panelNavState.readingMode) {
                                ReadingMode.PAGE -> "Page Reading Mode"
                                ReadingMode.PANEL -> "Panel Reading Mode"
                            },
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress Bar
                    LinearProgressIndicator(
                        progress = { panelNavState.getProgressPercentage() },
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress Info
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = panelNavState.getProgressText(),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Row {
                            // Previous Button
                            Button(
                                onClick = {
                                    when (panelNavState.readingMode) {
                                        ReadingMode.PAGE -> panelNavState.previousPage()
                                        ReadingMode.PANEL -> panelNavState.previousPanel()
                                    }
                                },
                                enabled = panelNavState.canGoPrevious(),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(
                                    when (panelNavState.readingMode) {
                                        ReadingMode.PAGE -> "Previous"
                                        ReadingMode.PANEL -> "Prev Panel"
                                    }
                                )
                            }

                            // Next Button
                            Button(
                                onClick = {
                                    when (panelNavState.readingMode) {
                                        ReadingMode.PAGE -> panelNavState.nextPage()
                                        ReadingMode.PANEL -> panelNavState.nextPanel()
                                    }
                                },
                                enabled = panelNavState.canGoNext()
                            ) {
                                Text(
                                    when (panelNavState.readingMode) {
                                        ReadingMode.PAGE -> "Next"
                                        ReadingMode.PANEL -> "Next Panel"
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
