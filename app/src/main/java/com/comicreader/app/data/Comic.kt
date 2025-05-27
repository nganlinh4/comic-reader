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
    val isLoaded: Boolean = false
)

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
    val samplePages = listOf(
        ComicPage("page1", "https://picsum.photos/800/1200?random=1", 1),
        ComicPage("page2", "https://picsum.photos/800/1200?random=2", 2),
        ComicPage("page3", "https://picsum.photos/800/1200?random=3", 3),
        ComicPage("page4", "https://picsum.photos/800/1200?random=4", 4),
        ComicPage("page5", "https://picsum.photos/800/1200?random=5", 5),
        ComicPage("page6", "https://picsum.photos/800/1200?random=6", 6),
        ComicPage("page7", "https://picsum.photos/800/1200?random=7", 7),
        ComicPage("page8", "https://picsum.photos/800/1200?random=8", 8),
        ComicPage("page9", "https://picsum.photos/800/1200?random=9", 9),
        ComicPage("page10", "https://picsum.photos/800/1200?random=10", 10)
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
            pages = samplePages.map { it.copy(id = "${it.id}_space", imageUrl = "https://picsum.photos/800/1200?random=${it.pageNumber + 20}") },
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
            pages = samplePages.map { it.copy(id = "${it.id}_urban", imageUrl = "https://picsum.photos/800/1200?random=${it.pageNumber + 30}") },
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
            pages = samplePages.map { it.copy(id = "${it.id}_mystic", imageUrl = "https://picsum.photos/800/1200?random=${it.pageNumber + 40}") },
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
            pages = samplePages.map { it.copy(id = "${it.id}_cyber", imageUrl = "https://picsum.photos/800/1200?random=${it.pageNumber + 50}") },
            genre = "Cyberpunk",
            publishDate = "2024-02-15",
            rating = 4.4f
        )
    )
}
