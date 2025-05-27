**Goal:** To build an immersive, delightful, and highly usable comic reading experience on Android by leveraging Material 3 Expressive components and styles.

**Overall Approach for a Comic Reader:**
1.  **Immersion is Key:** The primary goal of a comic reader is to let the user get lost in the story and art. Expressiveness should *enhance* this, not distract.
2.  **Content-First, UI Second:** While the UI should be expressive, it must never overshadow the comic pages themselves.
3.  **Performance Matters:** Smooth page turns and quick loading are critical. Expressive animations should be performant.

---

**Phase 1: Setup & Foundation**

1.  **Update Dependencies:**
    *   **Compose Material 3:** Ensure you're using at least `androidx.compose.material3:material3:1.4.0-alpha10` (or the latest available stable/beta/alpha version).
        ```gradle
        // For Compose
        implementation("androidx.compose.material3:material3:LATEST_VERSION") // Check official docs for the latest
        ```
    *   **Material Components for Android (Views):** If you have parts of your app in the View system, use `com.google.android.material:material:1.13-alpha11` (or latest).
        ```gradle
        // For Views (if needed)
        implementation("com.google.android.material:material:LATEST_VERSION")
        ```
    *   **Adaptive Navigation Suite (Compose):** For adaptive Nav Bar/Rail.
        ```gradle
        implementation("androidx.compose.material3:material3-adaptive-navigation-suite:LATEST_VERSION") // Check official docs
        ```
    *   *(Note: APIs marked experimental in alpha/beta releases might change. Always refer to the latest stable release notes when possible and test thoroughly.)*

2.  **Theme Setup:**
    *   **Color Scheme:**
        *   Use `MaterialExpressiveTheme` (if available as a direct import, otherwise configure `MaterialTheme` with expressive color/motion schemes).
        *   Implement `lightExpressiveColorScheme()` or `darkExpressiveColorScheme()` for your base static themes.
        *   **Comic-Specific Dynamic Color (Hero Feature):** Explore deriving accent colors from the current comic's cover art or dominant panel colors. This can be done by extracting prominent colors from the cover image bitmap and creating a custom `ColorScheme` on the fly for the reader screen. This significantly boosts immersion.
    *   **Motion Scheme:**
        *   In your Theme, set the `motionScheme` parameter.
        *   `MotionScheme.standard()` for most general UI interactions (library, settings).
        *   `MotionScheme.expressive()` for specific "delightful" moments: opening a comic, download completion, special achievements/unlocks. *Be cautious with overly bouncy animations during the core reading loop to avoid distraction.*
        ```kotlin
 духовной
        // Inside your AppTheme.kt or similar
        val dynamicColorScheme = // ... logic to get scheme from comic cover or fallback
        MaterialTheme( // Or MaterialExpressiveTheme if directly available
            colorScheme = dynamicColorScheme ?: if (isSystemInDarkTheme()) darkExpressiveColorScheme() else lightExpressiveColorScheme(),
            motionScheme = MotionScheme.standard(), // Default, can be overridden per component/screen
            typography = AppTypography, // Your defined typography
            shapes = AppShapes // Your defined shapes
        ) {
            // Your app content
        }
        ```
    *   **Typography:** Use clear, highly readable fonts for comic descriptions, dialogues (if app includes text rendering), and UI labels. Use Material 3's `Emphasized` type styles for series titles, chapter headings, or sound-effect-like callouts in app-specific features.
    *   **Shape:** Use subtle rounded corners for comic info cards in the library (e.g., `ShapeDefaults.Medium`). Keep reader UI elements distinct; avoid overly complex shapes that might clash with comic art.

---

**Phase 2: Implementing Expressive Components & Styles for a Comic Reader**

1.  **Components:**
    *   **App Bar & Toolbar:**
        *   **Reader Screen:** Minimalist `TopAppBar` (possibly auto-hiding) for chapter title/page number.
        *   **Library Screen:** `TopAppBar` for title, search, filter.
        *   `Toolbar` (Compose):
            *   **Reader Controls:** A floating `Toolbar` (bottom or side) for "Next/Prev Page," "Zoom," "Bookmark." Utilize expressive button shapes (e.g., slightly pill-shaped for next/prev) and tactile shape morphing on press.
    *   **Buttons:**
        *   **Page Navigation:** Large, clear "Next/Previous" buttons. Shape morphing provides excellent feedback.
        *   **Library Actions:** "Download," "Favorite," "Read."
        *   `Button Groups`:
            *   **Reader Settings:** For "Single/Double Page View," "Reading Direction." Use `Connected` style.
            *   **Library Filtering/Sorting.**
    *   **FAB & Extended FAB / FAB Menu:**
        *   **Library:** FAB for "Continue Reading." `FABMenu` for "Import," "Store," "Sort." The unfurling animation is a nice touch.
        *   **Reader:** Maybe a small, less intrusive FAB for "Bookmark."
    *   **Loading Indicator:**
        *   **Critical for Page/Comic Loading:** Use `LoadingIndicator()` when comic pages are buffering or full comics are downloading. Makes waiting feel more active.
    *   **Progress Indicators:**
        *   `CircularWavyProgressIndicator` for comic download progress displayed prominently.
    *   **Navigation Bar & Rail:**
        *   Destinations: "Library," "Reading Now," "Browse/Store," "Settings." The adaptive suite handles phone/tablet layouts well.
    *   **Carousel:**
        *   **Library:** Showcase comic covers for "Recently Read," "New Issues," or "Recommendations."
    *   **Split Button:**
        *   For a comic in the library: Primary "Read," dropdown for "Download," "Series Info," "Mark Unread."

2.  **Styles for a Comic Reader:**
    *   **Shape System:**
        *   **Cover Thumbnails:** `ShapeDefaults.Medium` or custom rounded shapes.
        *   **Iconic Shapes:** For genre tags, rating indicators, or UI accents that complement the comic aesthetic.
    *   **Motion System (Motion Physics):**
        *   **PAGE TURNS (Hero Motion):** This is where M3 Expressive motion shines.
            *   Use physics-based spring animations (`spring<Float>` spec) for page transitions (slide, fade-slide). Aim for a natural, tactile feel rather than rigid, instant changes.
            *   Access motion specs via `MaterialTheme.motionScheme` (or your custom scheme) for consistency.
        *   **Library Transitions:** When opening a comic, animate the cover expanding (`Modifier.sharedElement()` if feasible, or custom scale/fade animations using `animate*AsState`).
        *   **Zoom & Pan:** Ensure gestures are fluid, with subtle spring-back at boundaries using `Modifier.pointerInput` and `transformable`.
    *   **Color System:**
        *   **Reader UI Dynamic Theming:** Carefully apply extracted comic cover colors to reader controls, ensuring UI elements (icons, text) maintain high contrast and readability against *any* comic page art. Test with diverse comic styles.
        *   **Nuanced Surfaces:** Use tonal surface colors for toolbars overlaying comic pages to maintain legibility without full opacity.
    *   **Typography System:**
        *   **Emphasized styles** (`MaterialTheme.typography.displayLargeEmphasized`, etc.) for impactful series titles in the library or chapter intros.
        *   Ensure accessibility with dynamic font scaling and good contrast ratios.

---

**Phase 3: Hero Moments in a Comic Reader & Putting It All Together**

1.  **The Core Reading Experience:**
    *   **Page Turns:** Fluid, spring-based animations.
    *   **Reader Controls Toolbar:** Easy access, expressive buttons, possibly shape morphing.
    *   **Loading Pages:** Engaging `LoadingIndicator`.
2.  **Library Browsing & Discovery:**
    *   **Carousels with Cover Art:** Smooth scrolling, subtle item focus animations.
    *   **Opening a Comic:** Expressive spatial transition from library to reader.
3.  **Downloading & Interactions:**
    *   **Progress Indication:** `CircularWavyProgressIndicator`.
    *   **Download Complete/Favorite:** A brief, delightful animation (e.g., an icon pop or a subtle ripple).

---

**Phase 4: Resources & Learning (Verified Links)**

*   **Official Material Design 3 Guidelines:** [https://m3.material.io/](https://m3.material.io/)
    *   *Look for sections on Components, Styles (Shape, Color, Typography, Motion) to see Expressive features integrated.*
*   **Material 3 Design Kit (Figma):** [https://m3.material.io/resources/design-kit](https://m3.material.io/resources/design-kit)
    *   *Explore the expressive variants and new components visually.*
*   **Compose Material 3 Developer Documentation:** [https://developer.android.com/jetpack/compose/designsystems/material3](https://developer.android.com/jetpack/compose/designsystems/material3)
    *   *Find API details for implementing components and themes.*
*   **Material Design Blog:** [https://material.io/blog](https://material.io/blog)
    *   *Search for "Material 3 Expressive" or I/O '24 announcements for context and research insights.*
*   **Google Design Articles:** [https://design.google/](https://design.google/)
    *   *May host broader articles on design philosophy including expressiveness.*
*   **Google I/O Session Video (from your initial prompt):** Re-watch for visual cues and speaker emphasis.

---

**Key Coding Considerations for a Comic Reader:**

*   **Jetpack Compose `Pager`:** (`HorizontalPager` or `VerticalPager`) is essential for the core reading experience. Customize transitions using `PagerState` and modifiers.
*   **Image Handling (`Coil`, `Glide`):** Efficiently load, display, and cache comic pages. Pre-fetch adjacent pages for smooth transitions.
*   **Gesture Handling (`Modifier.pointerInput`, `transformable`):** For custom pan, zoom, and tap-to-reveal UI during reading.
*   **State Management:** For reader preferences (zoom, brightness, page layout), library state, download progress, and current comic progress.
*   **Performance Profiling:** Especially for page turns and image loading. Ensure animations are smooth (target 60+ fps). Use Android Studio profilers.
*   **Accessibility:** Robust zoom, content descriptions for all UI elements, ensuring good contrast with dynamic theming.
