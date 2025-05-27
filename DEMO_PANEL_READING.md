# Panel-by-Panel Reading Mode Demo

## How to Test the New Feature

### 1. Launch the App
- Open the Comic Reader app
- Navigate to the library screen
- Select any comic to open the reader

### 2. Switch to Panel Reading Mode
- Look for the **panel icon** (ViewQuilt) in the top app bar
- Tap it to switch from "Page Reading Mode" to "Panel Reading Mode"
- Notice the UI changes:
  - Bottom controls now show "Panel Reading Mode"
  - Navigation buttons change to "Prev Panel" and "Next Panel"
  - Progress text shows panel information

### 3. Panel Navigation Methods

#### Method 1: Tap Navigation
- **Tap any panel** on the comic page to jump directly to it
- The selected panel will be highlighted with a **red border**
- Other panels show **white borders** for reference

#### Method 2: Button Navigation
- Use **"Prev Panel"** button to go to previous panel
- Use **"Next Panel"** button to go to next panel
- Navigation automatically moves to next/previous page when needed

#### Method 3: Center Tap
- Tap the **center of the screen** (outside panels) to toggle UI visibility
- This works the same as in page mode

### 4. Panel Layouts to Test

The sample data includes 4 different panel layout types that cycle through the pages:

#### Page 1: Two Horizontal Panels
- Top panel: Upper half of the page
- Bottom panel: Lower half of the page

#### Page 2: Three Panels Layout
- Panel 1: Full width at top (35% height)
- Panel 2: Left bottom (47% width)
- Panel 3: Right bottom (47% width)

#### Page 3: Four Panel Grid
- Panel 1: Top-left quadrant
- Panel 2: Top-right quadrant
- Panel 3: Bottom-left quadrant
- Panel 4: Bottom-right quadrant

#### Page 4: Full Page Panel
- Single panel covering most of the page
- Good for splash pages or full-page art

### 5. Cross-Page Navigation
- Navigate to the **last panel** of a page
- Tap **"Next Panel"** to automatically move to the **first panel** of the next page
- Similarly, from the first panel of a page, "Prev Panel" goes to the last panel of the previous page

### 6. Switch Back to Page Mode
- Tap the **page icon** (ViewModule) in the top app bar
- Notice the mode switches back to "Page Reading Mode"
- Panel borders disappear
- Navigation returns to traditional left/right tap areas
- Progress shows page information only

### 7. Progress Tracking

#### In Panel Mode:
- Progress bar shows panel completion across entire comic
- Text shows: "Panel 2/3 • Page 1/10" format
- More granular progress tracking

#### In Page Mode:
- Progress bar shows page completion
- Text shows: "Page 1 of 10" format
- Traditional page-based progress

### 8. Visual Feedback

#### Panel Highlighting:
- **Current panel**: Red border with full opacity
- **Other panels**: White border with 30% opacity
- **Smooth animations**: Borders fade in/out when switching modes

#### UI Animations:
- Panel borders animate in when switching to panel mode
- Smooth transitions between panel selections
- Material 3 spring animations for natural feel

### 9. Edge Cases to Test

#### Pages with Different Panel Counts:
- Navigate through all pages to see different layouts
- Some pages have 1 panel, others have 2, 3, or 4 panels

#### Comic Boundaries:
- Try navigating past the last panel of the last page
- Try navigating before the first panel of the first page
- Buttons should disable appropriately

#### UI State:
- Toggle UI visibility in both modes
- Switch reading modes with UI hidden/shown
- Verify state persistence

### 10. Expected Behavior

#### Smooth Experience:
- No lag when switching between panels
- Responsive tap detection
- Accurate panel boundary detection

#### Intuitive Navigation:
- Clear visual feedback for current panel
- Logical panel reading order
- Seamless page transitions

#### Consistent UI:
- Reading mode clearly indicated
- Appropriate button labels
- Accurate progress information

## Troubleshooting

### If Panel Borders Don't Appear:
- Ensure you're in "Panel Reading Mode"
- Check that the current page has panels defined
- Try switching to a different page

### If Tap Detection Doesn't Work:
- Make sure you're tapping within panel boundaries
- Try tapping closer to panel centers
- Verify you're in panel reading mode

### If Navigation Seems Wrong:
- Check the progress text to confirm current position
- Try using button navigation instead of tap navigation
- Switch back to page mode and then to panel mode again

## Sample Data Overview

Each comic has 10 pages with the following panel layouts:
- **Pages 1, 5, 9**: 2 horizontal panels
- **Pages 2, 6, 10**: 3 panels (top full, bottom split)
- **Pages 3, 7**: 4 panel grid
- **Pages 4, 8**: Single full-page panel

This variety ensures you can test all different panel layout types and navigation scenarios.
