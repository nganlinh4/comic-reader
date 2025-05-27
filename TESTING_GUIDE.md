# 🧪 Comic Reader App - Testing Guide

## Prerequisites

### Required Software
- **Android Studio Hedgehog (2023.1.1) or later**
- **Java Development Kit (JDK) 17 or later**
- **Android SDK API 24+ (Android 7.0)**

## Method 1: Android Studio (Easiest)

### Step 1: Install Android Studio
1. Download from [developer.android.com/studio](https://developer.android.com/studio)
2. Run installer and follow setup wizard
3. Install Android SDK when prompted
4. Create or sign in to Google account for emulator

### Step 2: Open Project
1. Launch Android Studio
2. Select **"Open an existing project"**
3. Navigate to `c:\work\comic-reader`
4. Click **"OK"**

### Step 3: Sync Project
1. Android Studio will show "Gradle sync needed"
2. Click **"Sync Now"**
3. Wait for dependencies to download (may take 5-10 minutes first time)
4. Check for any sync errors in the "Build" tab

### Step 4: Set Up Device

#### Option A: Physical Device
1. Enable **Developer Options** on your Android device:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
   - Go back to Settings → Developer Options
   - Enable "USB Debugging"
2. Connect device via USB
3. Allow USB debugging when prompted

#### Option B: Virtual Device (Emulator)
1. Click **Tools → AVD Manager**
2. Click **"Create Virtual Device"**
3. Select **Phone → Pixel 7** (recommended)
4. Choose **API 34 (Android 14)** system image
5. Click **"Download"** if not installed
6. Name your AVD and click **"Finish"**

### Step 5: Run the App
1. Select your device from the device dropdown
2. Click the green **"Run"** button (▶️)
3. App will build and install automatically
4. First build may take 2-5 minutes

## Method 2: Command Line (Advanced)

### Prerequisites
```bash
# Install Java 17
# Windows: Download from Oracle or use Chocolatey
choco install openjdk17

# Set JAVA_HOME environment variable
# Windows: Add to System Environment Variables
JAVA_HOME=C:\Program Files\Java\jdk-17

# Install Android SDK
# Download command line tools from developer.android.com
```

### Build Commands
```bash
# Navigate to project
cd c:\work\comic-reader

# Make gradlew executable (Linux/Mac)
chmod +x gradlew

# Build debug APK
.\gradlew.bat assembleDebug

# Install on connected device
.\gradlew.bat installDebug

# Run tests
.\gradlew.bat test
```

## Method 3: Online Testing (No Setup Required)

### GitHub Codespaces
1. Push code to GitHub repository
2. Open in GitHub Codespaces
3. Use Android Studio in browser
4. Limited device testing options

### Replit Android
1. Import project to Replit
2. Use built-in Android emulator
3. Good for quick testing

## 🎯 What to Test

### Core Functionality
- [ ] **App Launch**: Opens without crashes
- [ ] **Library Screen**: Shows comic grid and carousel
- [ ] **Comic Cards**: Display covers, titles, ratings
- [ ] **Navigation**: Tap comic opens reader
- [ ] **Reader**: Page swiping works smoothly
- [ ] **Gestures**: Tap zones for navigation
- [ ] **Zoom**: Pinch to zoom in/out
- [ ] **UI Toggle**: Tap center to show/hide controls
- [ ] **Back Navigation**: Returns to library
- [ ] **Settings**: Opens settings screen

### Material 3 Features
- [ ] **Dynamic Colors**: Theme adapts to system
- [ ] **Animations**: Smooth page transitions
- [ ] **Cards**: Proper elevation and shadows
- [ ] **FAB**: Extended FAB in library
- [ ] **Progress**: Reading progress indicators
- [ ] **Typography**: Clear, readable text

### Performance
- [ ] **Smooth Scrolling**: 60fps in library
- [ ] **Fast Loading**: Images load quickly
- [ ] **Memory Usage**: No memory leaks
- [ ] **Battery**: Efficient power usage

## 🐛 Common Issues & Solutions

### Build Errors

#### "SDK not found"
```bash
# Solution: Update local.properties
sdk.dir=C\:\\Users\\[YourUsername]\\AppData\\Local\\Android\\Sdk
```

#### "Gradle sync failed"
```bash
# Solution: Clear cache
.\gradlew.bat clean
# Or in Android Studio: File → Invalidate Caches and Restart
```

#### "Compose compiler version mismatch"
```kotlin
// Solution: Update compose compiler in app/build.gradle.kts
composeOptions {
    kotlinCompilerExtensionVersion = "1.5.5"
}
```

### Runtime Issues

#### "App crashes on launch"
- Check Logcat in Android Studio
- Look for ClassNotFoundException or similar
- Ensure minimum SDK is 24+

#### "Images not loading"
- Check internet connection
- Verify Coil dependency
- Check network security config

#### "Gestures not working"
- Test on physical device (emulator gestures can be limited)
- Check touch targets are large enough
- Verify gesture detection code

## 📊 Testing Checklist

### Device Testing
- [ ] **Phone (Portrait)**: Primary use case
- [ ] **Phone (Landscape)**: Reading mode
- [ ] **Tablet**: Adaptive layout
- [ ] **Different Screen Sizes**: 5" to 12"
- [ ] **Different Android Versions**: API 24-34

### Accessibility Testing
- [ ] **TalkBack**: Screen reader support
- [ ] **Large Text**: Dynamic font scaling
- [ ] **High Contrast**: Color accessibility
- [ ] **Touch Targets**: Minimum 48dp

### Performance Testing
- [ ] **Memory Profiler**: Check for leaks
- [ ] **CPU Usage**: Smooth animations
- [ ] **Network**: Efficient image loading
- [ ] **Battery**: Background usage

## 🚀 Quick Start Commands

```bash
# Clone and test in one go
git clone [your-repo-url]
cd comic-reader

# Open in Android Studio
studio .

# Or build from command line
.\gradlew.bat assembleDebug
```

## 📱 Expected App Behavior

### Library Screen
- Shows 5 sample comics in grid
- Featured carousel at top
- "Continue Reading" section
- Smooth scrolling and animations
- FAB for quick access

### Reader Screen
- Full-screen comic pages
- Tap left/right for navigation
- Pinch to zoom
- Auto-hiding UI controls
- Progress indicator

### Settings Screen
- Toggle switches for preferences
- Material 3 card layouts
- Proper navigation

## 🎉 Success Indicators

✅ **App launches without errors**
✅ **All screens navigate properly**
✅ **Images load and display correctly**
✅ **Gestures work smoothly**
✅ **Material 3 theme applied**
✅ **Animations are fluid**
✅ **No crashes during normal use**

## 📞 Getting Help

If you encounter issues:

1. **Check Android Studio Logcat** for error messages
2. **Clean and rebuild** the project
3. **Update dependencies** to latest versions
4. **Test on different devices** to isolate issues
5. **Check GitHub Issues** for known problems

Happy testing! 🎉
