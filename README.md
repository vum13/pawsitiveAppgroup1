# pawsitiveAppgroup1
the app for Pawsitive pet academy 
# Pawsitive Pet Academy

An Android app for a pet care and business school in Durban, South Africa. Built with **Kotlin** and **Jetpack Compose**. Learners can browse short and long courses, request a quote, shop for pet products, check out, view a photo gallery (with CameraX), read reviews and blog posts, meet the team and get in touch.

---

## Contents

1. [Features](#features)
2. [Screens and navigation](#screens-and-navigation)
3. [Tech stack](#tech-stack)
4. [Getting started](#getting-started)
5. [Project structure](#project-structure)
6. [Design system](#design-system)
7. [Permissions](#permissions)
8. [How the main features work](#how-the-main-features-work)
9. [Customising the app](#customising-the-app)
10. [Troubleshooting](#troubleshooting)
11. [Known limitations](#known-limitations)

---

## Features

- **Courses:** five 6-week short courses and two 6-month courses, each with a detail page (content, duration, fee, what will be learned).
- **Enquiry and quote:** enter your details, tick the courses you want, and see a live quote with a multi-course discount. Submitting opens a pre-filled email to the academy.
- **Shop:** a product grid (food, toys, leashes) with category filters and "Add to Cart".
- **Cart and checkout:** change quantities, remove items, see subtotal, delivery and total, then pay (simulated).
- **Gallery:** take photos with CameraX or import from the photo picker. Photos are saved inside the app and shown in a grid.
- **Content pages:** About, Reviews, Our Team, Blog (tap to expand), Pricing and Contact (call, email, WhatsApp, Facebook and map shortcuts).

## Screens and navigation

| Screen | Route | Notes |

| Home | `home` | 2-column menu of 10 buttons, plus "Request a Quote" |
| About | `about` | History, vision, mission, goals |
| 6-Week Courses | `courses_weeks` | Toggle to 6-month list |
| 6-Month Courses | `courses_months` | Toggle to 6-week list |
| Course Detail | `course/{courseId}` | "Enquire Now" pre-selects the course |
| Gallery | `gallery` | Open Camera / Open Gallery |
| Contact | `contact` | Tappable contact rows |
| Enquiry | `enquiry?courseId={courseId}` | Optional pre-selected course |
| Shop | `shop` | `LazyVerticalGrid` |
| Checkout | `checkout` | Reached from the cart icon in the header |
| Reviews | `reviews` | |
| Our Team | `team` | |
| Blog | `blog` | |
| Pricing | `pricing` | All courses with fees |

Every screen shares one orange header with white text and a cart icon with an item-count badge. A purple bottom bar (Home, Courses, Shop, Gallery, Contact) shows everywhere except Checkout, which has its own purple payment bar.

## Tech stack

| Area | Choice |

| Language | Kotlin 2.0.21 |
| UI | Jetpack Compose (BOM 2024.10.01), Material 3 |
| Navigation | `navigation-compose` 2.8.4 |
| State | `ViewModel` + `StateFlow` |
| Camera | CameraX 1.4.0 |
| Images | Coil 2.7.0 |
| Build | Android Gradle Plugin 8.7.3, Gradle 8.9 |
| SDK | minSdk 24, compileSdk and targetSdk 35 |
| Java / Kotlin target | **JVM 21** |

## Getting started

### Requirements

- Android Studio **Ladybug (2024.2.1)** or newer
- **JDK 21** (bundled with recent Android Studio versions)
- An emulator or device running Android 7.0 (API 24) or higher
- An internet connection (product, gallery and course images load from the web)

### Run it

1. Unzip the project and open the `PawsitivePetAcademy` folder in Android Studio.
2. Go to **Settings > Build, Execution, Deployment > Build Tools > Gradle** and set **Gradle JDK** to **21**.
3. Click **Sync Project with Gradle Files**. Android Studio creates the Gradle wrapper files on first sync.
4. Choose a device and press **Run**.

### Build from the command line

```bash
./gradlew assembleDebug
```

The APK is written to `app/build/outputs/apk/debug/`.

## Project structure

```
PawsitivePetAcademy/
├── build.gradle.kts              Plugin versions
├── settings.gradle.kts
├── gradle.properties
├── gradle/wrapper/gradle-wrapper.properties
└── app/
    ├── build.gradle.kts          JVM 21, Compose, dependencies
    └── src/main/
        ├── AndroidManifest.xml   Permissions
        ├── res/                  Strings, theme, launcher icon
        └── java/com/pawsitive/petacademy/
            ├── MainActivity.kt
            ├── data/
            │   ├── Models.kt              Course, Product, Review, TeamMember, BlogPost
            │   ├── CourseRepository.kt    All course data
            │   ├── ContentRepository.kt   Products, gallery, reviews, team, blog
            │   └── PhotoStorage.kt        App-private photo folder
            ├── viewmodel/
            │   ├── CartViewModel.kt       Cart + simulated payment
            │   ├── EnquiryViewModel.kt    Form, validation, quote
            │   └── GalleryViewModel.kt    Photo list, gallery import
            ├── navigation/
            │   ├── Routes.kt
            │   └── PawsitiveApp.kt        Scaffold, header, bottom bar, NavHost
            └── ui/
                ├── theme/Theme.kt         Colour palette
                ├── components/            PawButton, PawImage, CourseCard, etc.
                └── screens/               One file per screen, plus CameraCapture
```

## Design system

| Colour | Hex | Used for |

| Cream | `#FFF6E9` | Screen background |
| Primary Orange | `#F5761A` | Headers, important buttons, prices |
| Light Blue | `#8DE4FF` | Course cards, cart table, order summary, form fields |
| Light Purple | `#A379C9` | Bottom navigation, checkout bar, secondary buttons |

All colours are defined once in `ui/theme/Theme.kt`. Prices on blue cards sit in a white pill so the orange stays readable.

## Permissions

Declared in `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.INTERNET" />
```

- **CAMERA** is requested at runtime when the user taps "Open Camera" in the Gallery. If it is denied, a message and an "Open app settings" button appear.
- **Importing photos** uses the Android photo picker, which does not need a storage permission. Picked images are copied into app storage.
- **INTERNET** is needed to load images.

## How the main features work

### Courses
All course data lives in `CourseRepository.kt`. Each course has an id, title, type (6-week or 6-month), duration, fee, content list, "what will be learned" text and image URL. Every course screen reads from the same lists.

### Cart and checkout
- `CartViewModel` is scoped to the Activity, so the cart survives navigation between Home, Shop and Checkout.
- **Delivery:** R60, free when the subtotal is R500 or more.
- **Total:** subtotal plus delivery.
- **Pay Now:** shows a progress indicator for about 2 seconds, then a success dialog with an order reference, and empties the cart. No real payment is taken.

### Enquiry and quote
- Name, phone and email are validated before submitting.
- The quote updates as courses are ticked.
- **Discount:** 5% for 2 courses, 10% for 3 or more.
- **Submit Enquiry** opens the user's email app with the details pre-filled, addressed to the academy.

### Gallery and camera
- **Open Camera** checks the CAMERA permission, then shows a full-screen CameraX preview with capture, close and front/back switch.
- **Open Gallery** opens the photo picker for up to 10 images.
- Photos are stored in the app's private `files/photos` folder, so they are still there after a restart.

## Customising the app

| I want to... | Edit |

| Add or change a course | `data/CourseRepository.kt` (add a `Course(...)` entry; up to 5 six-month courses are supported by the design) |
| Add or change shop products | `data/ContentRepository.kt` (`products`) |
| Change delivery fee or free-delivery limit | `CartViewModel.kt` (`DELIVERY_FEE`, `FREE_DELIVERY_THRESHOLD`) |
| Change discount rules | `EnquiryViewModel.kt` (`Quote.from`) |
| Change contact details | `ui/screens/ContactScreen.kt` (constants at the top) |
| Change the email that receives enquiries | `ui/screens/EnquiryScreen.kt` (`ACADEMY_EMAIL`) |
| Use your own images | Replace the `imageUrl` strings in `data/` with your own URLs, or put images in `res/drawable` and load them with `painterResource` |
| Change colours | `ui/theme/Theme.kt` |

## Troubleshooting

- **"Unsupported class file major version" or Java errors:** the Gradle JDK is not 21. Set it as described in [Run it](#run-it).
- **Images show a paw icon:** the device has no internet, or the image URL could not load. Check the connection or replace the URL.
- **Camera shows nothing on the emulator:** enable a virtual camera in the AVD settings (Advanced Settings > Camera), or test on a real device.
- **Contact buttons show "No app found":** the emulator has no dialer, email, WhatsApp or maps app installed.
- **Sync fails to download dependencies:** check your network or proxy settings in Android Studio.

## Known limitations

- Payments are simulated; there is no payment gateway or backend.
- The cart and enquiry form are not saved between app launches.
- Reviews, blog posts and team details are bundled with the app and are not loaded from a server.
- The Facebook link (`facebook.com/pawsitivepetacademy`) is a placeholder. Replace it with the real page.
- This project was written without a build environment, so the first sync may need small fixes.
