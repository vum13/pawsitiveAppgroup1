# Pawsitive Pet Academy (Kotlin + Jetpack Compose)

Open this folder in Android Studio (Ladybug 2024.2 or newer), set **Gradle JDK = 21**
(Settings > Build > Gradle > Gradle JDK), then Sync and Run.
Android Studio generates the Gradle wrapper jar/scripts on first sync.

- AGP 8.7.3, Kotlin 2.0.21, Gradle 8.9, compileSdk/targetSdk 35, minSdk 24
- Java/Kotlin target: JVM 21
- Product, gallery and course images load from the internet (loremflickr.com) with a paw fallback.
  Swap `imageUrl` values in `data/` for your own images or drawables when you have them.
