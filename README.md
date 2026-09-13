# MinimalDate-widget
A widget for date and time which is very lightweight and changes font every 5 hours

# Date Widget

A simple Android home-screen widget that displays the date/time with a customizable font.

## Prerequisites

Before building the project, make sure you have:

* **Android Studio** installed
* **JDK 17** installed
* **Android SDK** installed through Android Studio
* An Android device or emulator for testing
* USB debugging enabled if installing directly on a physical device

You don't need to install Gradle separately. The project uses the included **Gradle Wrapper**.

## Build APK

Clone the repository and open a terminal in the project directory:

```cmd
cd DateWidget
```

### Clean the project

```cmd
gradlew.bat clean
```

### Generate Debug APK

```cmd
gradlew.bat assembleDebug
```

The APK will be generated at:

```text
app\build\outputs\apk\debug\app-debug.apk
```

You can copy this APK to your Android phone and install it.

## Install Using ADB

If ADB is configured and USB debugging is enabled:

```cmd
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

## Build Release APK

To generate a release APK:

```cmd
gradlew.bat assembleRelease
```

The output will be under:

```text
app\build\outputs\apk\release\
```

> A release APK may require signing configuration before it can be distributed or published to Google Play.

## Android Studio

Alternatively:

1. Open the project in Android Studio.
2. Allow Gradle to sync.
3. Connect an Android device or start an emulator.
4. Click **Run** to install the app.
5. Add the widget from the Android home-screen widget picker.

## Project Structure

```text
DateWidget/
├── app/
│   ├── src/
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
└── README.md
```

