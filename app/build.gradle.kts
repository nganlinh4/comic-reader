plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.comicreader.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.comicreader.app"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.0")
    implementation("androidx.activity:activity-compose:1.11.0-rc01")

    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.12.01")) // Keeping the same BOM version as no newer one was found to significantly alter sub-dependencies.
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Material 3 Expressive
    implementation("androidx.compose.material3:material3:1.3.2")
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.4.0-alpha15")
    implementation("com.google.android.material:material:1.14.0-alpha01")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.9.0")

    // Pager for comic reading
    implementation("androidx.compose.foundation:foundation")

    // Image loading
    implementation("io.coil-kt.coil3:coil-compose:3.2.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0")

    // Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.36.0") // No newer version found, keeping current

    // Testing
    testImplementation("junit:junit:4.13.2") // Latest for JUnit 4
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0-alpha03")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.12.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}