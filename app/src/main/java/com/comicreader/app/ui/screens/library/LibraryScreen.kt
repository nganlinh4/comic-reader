package com.comicreader.app.ui.screens.library

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.comicreader.app.data.Comic
import com.comicreader.app.data.SampleComicData
import com.comicreader.app.ui.components.ComicCard
import com.comicreader.app.ui.components.FeaturedComicCarousel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LibraryScreen(
    onComicClick: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val comics = remember { SampleComicData.sampleComics }
    val recentComics = remember { comics.take(3) }
    val featuredComics = remember { comics.shuffled().take(5) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar with search
        TopAppBar(
            title = {
                Text(
                    "Comic Library",
                    style = MaterialTheme.typography.headlineMedium
                )
            },
            actions = {
                IconButton(onClick = { /* TODO: Implement search */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Featured Comics Carousel
            item {
                Column {
                    Text(
                        text = "Featured",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    FeaturedComicCarousel(
                        comics = featuredComics,
                        onComicClick = onComicClick
                    )
                }
            }

            // Continue Reading Section
            item {
                Column {
                    Text(
                        text = "Continue Reading",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        items(recentComics) { comic ->
                            ComicCard(
                                comic = comic,
                                onClick = { onComicClick(comic.id) },
                                modifier = Modifier.width(120.dp)
                            )
                        }
                    }
                }
            }

            // All Comics Grid
            item {
                Text(
                    text = "All Comics",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            item {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.height(600.dp) // Fixed height for nested scrolling
                ) {
                    items(comics) { comic ->
                        ComicCard(
                            comic = comic,
                            onClick = { onComicClick(comic.id) }
                        )
                    }
                }
            }
        }

        // FAB for Continue Reading
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            ExtendedFloatingActionButton(
                onClick = {
                    // Navigate to last read comic
                    if (recentComics.isNotEmpty()) {
                        onComicClick(recentComics.first().id)
                    }
                },
                icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                text = { Text("Continue Reading") },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
