# QuickUtility App

## Description

**QuickUtility App** is a multi-functional utility Android application designed to provide users with tools for everyday tasks. This app includes various features such as a Calendar, Calculator, Unit Converter, and Flash Events. Additionally, it offers customizable settings (light/dark theme) and a help section to guide users through the app.

## Features

- **Calendar**: Add and view events for specific dates. Events can be created by tapping on a date, and they are displayed in a list for easy management. Events are erased when the app is closed.
- **Calculator**: A dual-mode calculator that supports both basic arithmetic operations and advanced scientific calculations (e.g., trigonometric functions, logarithms).
- **Converter**: Perform real-time conversions between common units such as length, weight, and temperature without saving any conversion history.
- **Flash Events**: Create quick events with titles and times, edit or delete events, and manage them in a list format. Events are erased after app closure.
- **Settings**: Customize the app's appearance by switching between light and dark themes.
- **Help**: A detailed help section that provides users with instructions on how to use each feature in the app.

## Technologies Used

- **Android SDK**: Android 5.0 (API level 21) or higher.
- **Kotlin**: The main programming language used for development.
- **Android Jetpack Components**: Including `Fragment`, `RecyclerView`, `CalendarView`, and `BottomNavigationView`.
- **Material Design**: Used to create a sleek and modern UI using components like `FloatingActionButton` and `TabLayout`.
- **Monkey Tool**: Used for stress testing by generating random events in the app.
- **Lint Tool**: Used for code inspection to ensure the app adheres to Android development best practices.

## Installation

### Prerequisites

- Android Studio installed on your system.
- Android SDK and necessary tools installed.

### Steps

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/your-username/QuickUtility.git

2. Open the project in Android Studio.
3. Build the project by syncing Gradle files. 
4. Connect your Android device or use an emulator. 
5. Run the project on your device or emulator using Android Studio’s run option.


## Usage
Once the app is installed, you can use its various features:

- **Calendar:** Navigate to the Calendar tab, select a date, and add events.
- **Calculator:** Use the Calculator tab for basic or scientific calculations.
- **Converter:** Convert units such as length, weight, or temperature in real-time.
- **Flash Events:** Quickly create and manage short-term events in the Flash Events tab.
- **Settings:** Change the app’s theme (light or dark) through the overflow menu in the action bar.
- **Help:** Access the Help section via the overflow menu for assistance on how to use the app.

## Testing

Stress Test
The Monkey Tool was used to perform stress testing on the app by simulating random user events. This ensures that the app remains stable and responsive under unexpected usage patterns.

### Test command:

    ```bash
    adb shell monkey -p com.madassignment.quickutility -v 500

## Code Inspection
The Lint Tool was used for code inspection to check for performance issues, deprecated APIs, accessibility concerns, and unused resources. The app passed all tests with minimal warnings.
