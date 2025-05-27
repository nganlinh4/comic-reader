package com.comicreader.app.data

import androidx.compose.runtime.Immutable

@Immutable
data class Comic(
    val id: String,
    val title: String,
    val author: String,
    val description: String,
    val coverImageUrl: String,
    val pages: List<ComicPage>,
    val currentPage: Int = 0,
    val isDownloaded: Boolean = false,
    val isFavorite: Boolean = false,
    val genre: String = "",
    val publishDate: String = "",
    val rating: Float = 0f,
    val totalPages: Int = pages.size
)

@Immutable
data class ComicPage(
    val id: String,
    val imageUrl: String,
    val pageNumber: Int,
    val isLoaded: Boolean = false,
    val panels: List<ComicPanel> = emptyList()
)

@Immutable
data class ComicPanel(
    val id: String,
    val pageId: String,
    val panelNumber: Int,
    val bounds: PanelBounds,
    val readingOrder: Int = panelNumber
)

@Immutable
data class PanelBounds(
    val left: Float,    // Percentage from left (0.0 to 1.0)
    val top: Float,     // Percentage from top (0.0 to 1.0)
    val right: Float,   // Percentage from left (0.0 to 1.0)
    val bottom: Float   // Percentage from top (0.0 to 1.0)
)

enum class ReadingMode {
    PAGE,
    PANEL
}

@Immutable
data class ComicSeries(
    val id: String,
    val title: String,
    val description: String,
    val coverImageUrl: String,
    val comics: List<Comic>,
    val author: String,
    val genre: String,
    val isCompleted: Boolean = false
)

// Sample data for demonstration
object SampleComicData {
    // Sample panel layouts for different page types
    private fun createSamplePanels(pageId: String, layoutType: Int): List<ComicPanel> {
        return when (layoutType % 4) {
            0 -> listOf(
                // Full page panel
                ComicPanel("${pageId}_panel1", pageId, 1, PanelBounds(0.05f, 0.05f, 0.95f, 0.95f))
            )
            1 -> listOf(
                // Two horizontal panels
                ComicPanel("${pageId}_panel1", pageId, 1, PanelBounds(0.05f, 0.05f, 0.95f, 0.48f)),
                ComicPanel("${pageId}_panel2", pageId, 2, PanelBounds(0.05f, 0.52f, 0.95f, 0.95f))
            )
            2 -> listOf(
                // Three panels - top full, bottom split
                ComicPanel("${pageId}_panel1", pageId, 1, PanelBounds(0.05f, 0.05f, 0.95f, 0.35f)),
                ComicPanel("${pageId}_panel2", pageId, 2, PanelBounds(0.05f, 0.38f, 0.48f, 0.95f)),
                ComicPanel("${pageId}_panel3", pageId, 3, PanelBounds(0.52f, 0.38f, 0.95f, 0.95f))
            )
            else -> listOf(
                // Four panels in grid
                ComicPanel("${pageId}_panel1", pageId, 1, PanelBounds(0.05f, 0.05f, 0.48f, 0.48f)),
                ComicPanel("${pageId}_panel2", pageId, 2, PanelBounds(0.52f, 0.05f, 0.95f, 0.48f)),
                ComicPanel("${pageId}_panel3", pageId, 3, PanelBounds(0.05f, 0.52f, 0.48f, 0.95f)),
                ComicPanel("${pageId}_panel4", pageId, 4, PanelBounds(0.52f, 0.52f, 0.95f, 0.95f))
            )
        }
    }

    val samplePages = listOf(
        ComicPage("page1", "https://picsum.photos/800/1200?random=1", 1, panels = createSamplePanels("page1", 1)),
        ComicPage("page2", "https://picsum.photos/800/1200?random=2", 2, panels = createSamplePanels("page2", 2)),
        ComicPage("page3", "https://picsum.photos/800/1200?random=3", 3, panels = createSamplePanels("page3", 3)),
        ComicPage("page4", "https://picsum.photos/800/1200?random=4", 4, panels = createSamplePanels("page4", 0)),
        ComicPage("page5", "https://picsum.photos/800/1200?random=5", 5, panels = createSamplePanels("page5", 1)),
        ComicPage("page6", "https://picsum.photos/800/1200?random=6", 6, panels = createSamplePanels("page6", 2)),
        ComicPage("page7", "https://picsum.photos/800/1200?random=7", 7, panels = createSamplePanels("page7", 3)),
        ComicPage("page8", "https://picsum.photos/800/1200?random=8", 8, panels = createSamplePanels("page8", 0)),
        ComicPage("page9", "https://picsum.photos/800/1200?random=9", 9, panels = createSamplePanels("page9", 1)),
        ComicPage("page10", "https://picsum.photos/800/1200?random=10", 10, panels = createSamplePanels("page10", 2))
    )

    val sampleComics = listOf(
        Comic(
            id = "comic1",
            title = "The Amazing Adventures",
            author = "John Doe",
            description = "An epic tale of heroism and adventure in a fantastical world filled with magic and mystery.",
            coverImageUrl = "https://picsum.photos/400/600?random=11",
            pages = samplePages,
            genre = "Fantasy",
            publishDate = "2024-01-15",
            rating = 4.5f
        ),
        Comic(
            id = "comic2",
            title = "Space Odyssey",
            author = "Jane Smith",
            description = "A thrilling journey through the cosmos as humanity explores the final frontier.",
            coverImageUrl = "https://picsum.photos/400/600?random=12",
            pages = samplePages.map { page ->
                page.copy(
                    id = "${page.id}_space",
                    imageUrl = "https://picsum.photos/800/1200?random=${page.pageNumber + 20}",
                    panels = page.panels.map { panel ->
                        panel.copy(id = "${panel.id}_space", pageId = "${page.id}_space")
                    }
                )
            },
            genre = "Sci-Fi",
            publishDate = "2024-02-01",
            rating = 4.8f
        ),
        Comic(
            id = "comic3",
            title = "Urban Legends",
            author = "Mike Johnson",
            description = "Dark tales from the city streets where ordinary people face extraordinary circumstances.",
            coverImageUrl = "https://picsum.photos/400/600?random=13",
            pages = samplePages.map { page ->
                page.copy(
                    id = "${page.id}_urban",
                    imageUrl = "https://picsum.photos/800/1200?random=${page.pageNumber + 30}",
                    panels = page.panels.map { panel ->
                        panel.copy(id = "${panel.id}_urban", pageId = "${page.id}_urban")
                    }
                )
            },
            genre = "Mystery",
            publishDate = "2024-01-20",
            rating = 4.2f
        ),
        Comic(
            id = "comic4",
            title = "Mystic Realms",
            author = "Sarah Wilson",
            description = "Ancient magic awakens in the modern world, bringing wonder and danger in equal measure.",
            coverImageUrl = "https://picsum.photos/400/600?random=14",
            pages = samplePages.map { page ->
                page.copy(
                    id = "${page.id}_mystic",
                    imageUrl = "https://picsum.photos/800/1200?random=${page.pageNumber + 40}",
                    panels = page.panels.map { panel ->
                        panel.copy(id = "${panel.id}_mystic", pageId = "${page.id}_mystic")
                    }
                )
            },
            genre = "Fantasy",
            publishDate = "2024-03-01",
            rating = 4.6f
        ),
        Comic(
            id = "comic5",
            title = "Cyber Chronicles",
            author = "Alex Chen",
            description = "In a dystopian future, hackers and AI fight for control of the digital realm.",
            coverImageUrl = "https://picsum.photos/400/600?random=15",
            pages = samplePages.map { page ->
                page.copy(
                    id = "${page.id}_cyber",
                    imageUrl = "https://picsum.photos/800/1200?random=${page.pageNumber + 50}",
                    panels = page.panels.map { panel ->
                        panel.copy(id = "${panel.id}_cyber", pageId = "${page.id}_cyber")
                    }
                )
            },
            genre = "Cyberpunk",
            publishDate = "2024-02-15",
            rating = 4.4f
        )
    )
}
