# Native Color Analyzer Camera

A modern, native Android application built with Kotlin that leverages the device camera to capture images and utilizes Google's ML Kit to analyze and identify real-time color data.

---

##  Features

* **Native Camera Integration:** High-performance image capture and stream processing.
* **ML Kit Intelligence:** Advanced computer vision processing to detect, analyze, and name colors from photos.
* **Interactive Manipulation:** Analyze standard captures, extract hex codes, and dynamically change/manipulate the color outputs.
* **Modern Android Architecture:** Built purely with modern declarative UI and cutting-edge reactive design patterns.

---

## 🛠️ Tech Stack & Architecture

This project showcases a production-ready reactive architecture combining the best of asynchronous paradigms in the Android ecosystem:

* **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) - Clean, declarative UI components.
* **Language:** [Kotlin](https://kotlinlang.org/) - 100% native development.
* **Asynchronous Streams:** Dual integration using both **RxJava** and **Kotlin Coroutines / Flow** for robust, multi-threaded data pipelines and state management.
* **Machine Learning:** [Google ML Kit](https://developers.google.com/ml-kit) - On-device computer vision for blazing-fast image processing without server latency.

---

##  How It Works

1. **Capture:** The user opens the camera view and takes a picture.
2. **Analyze:** The image payload is passed instantly to the ML Kit vision pipeline.
3. **Identify:** The app extracts dominant color clusters, processes the RGB/Hex data, and returns the closest human-readable color name.
4. **Manipulate:** Users can adjust or interact with the analyzed color output within the reactive UI.

---

##  Getting Started

### Prerequisites
* Android Studio (Ladybug or newer)
* Android SDK Level 24+ (Android 7.0)
* A physical Android device (recommended for testing camera/ML Kit features)

### Installation
1. Clone the repository:
   ```bash
   git clone [https://github.com/Jaakko99/native-camera.git](https://github.com/Jaakko99/native-camera.git)
