package com.comicreader.app.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.comicreader.app.data.Comic
import com.comicreader.app.data.ComicPanel
import com.comicreader.app.data.ReadingMode

@Stable
class PanelNavigationState(
    private val comic: Comic,
    initialPageIndex: Int = 0,
    initialReadingMode: ReadingMode = ReadingMode.PAGE
) {
    var readingMode by mutableStateOf(initialReadingMode)
        private set
    
    var currentPageIndex by mutableIntStateOf(initialPageIndex)
        private set
    
    var currentPanelIndex by mutableIntStateOf(0)
        private set
    
    // Flattened list of all panels across all pages for easy navigation
    private val allPanels: List<PanelWithPageInfo> by lazy {
        comic.pages.flatMapIndexed { pageIndex, page ->
            page.panels.mapIndexed { panelIndex, panel ->
                PanelWithPageInfo(
                    panel = panel,
                    pageIndex = pageIndex,
                    panelIndexInPage = panelIndex,
                    globalPanelIndex = comic.pages.take(pageIndex).sumOf { it.panels.size } + panelIndex
                )
            }
        }
    }
    
    val currentPage get() = comic.pages.getOrNull(currentPageIndex)
    val currentPanel get() = currentPage?.panels?.getOrNull(currentPanelIndex)
    val totalPanels get() = allPanels.size
    val currentGlobalPanelIndex get() = allPanels.find { 
        it.pageIndex == currentPageIndex && it.panelIndexInPage == currentPanelIndex 
    }?.globalPanelIndex ?: 0
    
    fun toggleReadingMode() {
        readingMode = when (readingMode) {
            ReadingMode.PAGE -> ReadingMode.PANEL
            ReadingMode.PANEL -> ReadingMode.PAGE
        }
        
        // Reset panel index when switching to page mode
        if (readingMode == ReadingMode.PAGE) {
            currentPanelIndex = 0
        }
    }
    
    fun setReadingMode(mode: ReadingMode) {
        readingMode = mode
        if (mode == ReadingMode.PAGE) {
            currentPanelIndex = 0
        }
    }
    
    fun goToPage(pageIndex: Int) {
        if (pageIndex in 0 until comic.pages.size) {
            currentPageIndex = pageIndex
            currentPanelIndex = 0
        }
    }
    
    fun goToPanel(pageIndex: Int, panelIndex: Int) {
        val page = comic.pages.getOrNull(pageIndex)
        if (page != null && panelIndex in 0 until page.panels.size) {
            currentPageIndex = pageIndex
            currentPanelIndex = panelIndex
        }
    }
    
    fun nextPanel(): Boolean {
        if (readingMode != ReadingMode.PANEL) return false
        
        val currentPage = comic.pages.getOrNull(currentPageIndex) ?: return false
        
        // Try to go to next panel on current page
        if (currentPanelIndex < currentPage.panels.size - 1) {
            currentPanelIndex++
            return true
        }
        
        // Try to go to first panel of next page
        if (currentPageIndex < comic.pages.size - 1) {
            val nextPage = comic.pages[currentPageIndex + 1]
            if (nextPage.panels.isNotEmpty()) {
                currentPageIndex++
                currentPanelIndex = 0
                return true
            }
        }
        
        return false
    }
    
    fun previousPanel(): Boolean {
        if (readingMode != ReadingMode.PANEL) return false
        
        // Try to go to previous panel on current page
        if (currentPanelIndex > 0) {
            currentPanelIndex--
            return true
        }
        
        // Try to go to last panel of previous page
        if (currentPageIndex > 0) {
            val previousPage = comic.pages[currentPageIndex - 1]
            if (previousPage.panels.isNotEmpty()) {
                currentPageIndex--
                currentPanelIndex = previousPage.panels.size - 1
                return true
            }
        }
        
        return false
    }
    
    fun nextPage(): Boolean {
        if (currentPageIndex < comic.pages.size - 1) {
            currentPageIndex++
            currentPanelIndex = 0
            return true
        }
        return false
    }
    
    fun previousPage(): Boolean {
        if (currentPageIndex > 0) {
            currentPageIndex--
            currentPanelIndex = 0
            return true
        }
        return false
    }
    
    fun canGoNext(): Boolean {
        return when (readingMode) {
            ReadingMode.PAGE -> currentPageIndex < comic.pages.size - 1
            ReadingMode.PANEL -> {
                val currentPage = comic.pages.getOrNull(currentPageIndex)
                currentPage != null && (
                    currentPanelIndex < currentPage.panels.size - 1 ||
                    currentPageIndex < comic.pages.size - 1
                )
            }
        }
    }
    
    fun canGoPrevious(): Boolean {
        return when (readingMode) {
            ReadingMode.PAGE -> currentPageIndex > 0
            ReadingMode.PANEL -> currentPageIndex > 0 || currentPanelIndex > 0
        }
    }
    
    fun getProgressPercentage(): Float {
        return when (readingMode) {
            ReadingMode.PAGE -> (currentPageIndex + 1).toFloat() / comic.pages.size
            ReadingMode.PANEL -> {
                if (totalPanels == 0) 0f
                else (currentGlobalPanelIndex + 1).toFloat() / totalPanels
            }
        }
    }
    
    fun getProgressText(): String {
        return when (readingMode) {
            ReadingMode.PAGE -> "Page ${currentPageIndex + 1} of ${comic.pages.size}"
            ReadingMode.PANEL -> {
                val currentPage = comic.pages.getOrNull(currentPageIndex)
                if (currentPage != null && currentPage.panels.isNotEmpty()) {
                    "Panel ${currentPanelIndex + 1}/${currentPage.panels.size} • Page ${currentPageIndex + 1}/${comic.pages.size}"
                } else {
                    "Page ${currentPageIndex + 1} of ${comic.pages.size}"
                }
            }
        }
    }
}

@Stable
data class PanelWithPageInfo(
    val panel: ComicPanel,
    val pageIndex: Int,
    val panelIndexInPage: Int,
    val globalPanelIndex: Int
)

@Composable
fun rememberPanelNavigationState(
    comic: Comic,
    initialPageIndex: Int = 0,
    initialReadingMode: ReadingMode = ReadingMode.PAGE
): PanelNavigationState {
    return remember(comic.id) {
        PanelNavigationState(
            comic = comic,
            initialPageIndex = initialPageIndex,
            initialReadingMode = initialReadingMode
        )
    }
}
