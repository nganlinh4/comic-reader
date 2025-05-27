# Comic Reader - Material 3 Expressive

A modern Android comic reading app built with **Material 3 Expressive** components and **Jetpack Compose**.

## Features

### 🎨 Material 3 Expressive Design
- **Dynamic Color Theming**: Adapts to comic cover colors for immersive reading
- **Expressive Animations**: Smooth page turns with physics-based spring animations
- **Modern UI Components**: Cards, carousels, and adaptive navigation

### 📚 Comic Library
- **Featured Comics Carousel**: Discover new comics with beautiful cover art
- **Continue Reading**: Quick access to your current reads
- **Grid Layout**: Browse your entire collection
- **Search & Filter**: Find comics by title, author, or genre

### 📖 Immersive Reading Experience
- **Gesture Controls**: Tap to navigate, pinch to zoom
- **Auto-hiding UI**: Distraction-free reading mode
- **Page Progress**: Visual progress indicator
- **Smooth Transitions**: Physics-based page turning animations

### ⚙️ Customization
- **Dark/Light Theme**: Automatic or manual theme switching
- **Reading Preferences**: Customize your reading experience
- **Notifications**: Stay updated with new releases

## Technical Implementation

### Architecture
- **MVVM Pattern**: Clean separation of concerns
- **Jetpack Compose**: Modern declarative UI
- **Navigation Component**: Type-safe navigation
- **Material 3**: Latest design system

### Key Components
- **HorizontalPager**: Smooth page turning with gesture support
- **Coil**: Efficient image loading and caching
- **Transformable**: Zoom and pan gestures
- **Adaptive Navigation**: Responsive design for different screen sizes

### Material 3 Expressive Features
- **Dynamic Color Schemes**: Extract colors from comic covers
- **Motion System**: Spring-based animations for natural feel
- **Expressive Typography**: Emphasized styles for comic titles
- **Shape System**: Rounded corners and custom shapes

## Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 24+
- Kotlin 1.9.20+

### Building the Project
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on device or emulator

### Dependencies
- **Compose BOM**: 2023.10.01
- **Material 3**: 1.2.0-alpha12
- **Navigation Compose**: 2.7.5
- **Coil**: 2.5.0
- **Accompanist**: 0.32.0

## Project Structure

```
app/
├── src/main/java/com/comicreader/app/
│   ├── data/                 # Data models and sample data
│   ├── navigation/           # Navigation setup
│   ├── ui/
│   │   ├── components/       # Reusable UI components
│   │   ├── screens/          # Screen composables
│   │   └── theme/           # Material 3 theme configuration
│   └── MainActivity.kt
└── src/main/res/            # Resources (strings, colors, etc.)
```

## Key Features Implementation

### Dynamic Color Theming
The app extracts dominant colors from comic covers to create immersive, context-aware themes:

```kotlin
// Extract colors from comic cover and create dynamic color scheme
val dynamicColorScheme = extractColorsFromCover(comic.coverImageUrl)
MaterialTheme(colorScheme = dynamicColorScheme) { ... }
```

### Physics-Based Page Turns
Smooth, natural page transitions using Material 3's motion system:

```kotlin
val pagerState = rememberPagerState()
HorizontalPager(
    state = pagerState,
    modifier = Modifier.transformable(transformableState)
) { page -> ... }
```

### Gesture Controls
Intuitive reading controls with tap zones and zoom gestures:

```kotlin
Modifier.pointerInput(Unit) {
    detectTapGestures { tapOffset ->
        when {
            tapOffset.x < screenWidth * 0.3f -> previousPage()
            tapOffset.x > screenWidth * 0.7f -> nextPage()
            else -> toggleUI()
        }
    }
}
```

## Design Philosophy

Following the plan's core principles:

1. **Immersion is Key**: UI enhances the story, never distracts
2. **Content-First**: Comic pages are the star, UI is supportive
3. **Performance Matters**: Smooth 60fps animations and quick loading

## Future Enhancements

- [ ] Cloud sync for reading progress
- [ ] Offline reading with smart caching
- [ ] Social features (reviews, recommendations)
- [ ] Advanced reading modes (panel-by-panel)
- [ ] Accessibility improvements
- [ ] Tablet-optimized layouts

## Contributing

1. Fork the repository
2. Create a feature branch
3. Follow Material 3 design guidelines
4. Test on multiple screen sizes
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- **Material Design Team** for the excellent Material 3 Expressive guidelines
- **Android Jetpack Compose Team** for the modern UI toolkit
- **Sample comic data** provided by Lorem Picsum for demonstration

---

Built with ❤️ using Material 3 Expressive and Jetpack Compose
